package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.UncheckedAlohaException;
import io.bayberry.aloha.exception.UnsupportedListenerException;
import io.bayberry.aloha.exception.UnsupportedMessageException;
import io.bayberry.aloha.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.spring.util.ExpressionInterpreter;
import io.bayberry.aloha.support.AsyncStreamDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

public class RedisMessageBus extends RemoteMessageBus<Object, byte[]> implements ApplicationContextAware, InitializingBean {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private ApplicationContext applicationContext;
    private RedisTemplate<String, byte[]> redisTemplate;
    private RedisConnectionFactory redisConnectionFactory;
    private RedisSubscribableStreamContainer redisSubscribableStreamContainer;
    private ProduceCommand produceCommand;
    private PublishCommand publishCommand;
    private RedisMessageBusOptions options;
    private ExpressionInterpreter expressionInterpreter;

    public RedisMessageBus(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, DEFAULT_SETTINGS);
    }

    public RedisMessageBus(RedisConnectionFactory connectionFactory, RedisMessageBusOptions options) {
        this.redisConnectionFactory = connectionFactory;
        this.options = options;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.expressionInterpreter = new ExpressionInterpreter(this.applicationContext);
        this.redisTemplate = new RedisTemplate<>();
        this.redisTemplate.setConnectionFactory(this.redisConnectionFactory);
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setEnableDefaultSerializer(false);
        this.redisTemplate.afterPropertiesSet();
        this.redisSubscribableStreamContainer = new RedisSubscribableStreamContainer();
        this.produceCommand = new ProduceCommand();
        this.publishCommand = new PublishCommand();
        this.applicationContext.getBeansWithAnnotation(RedisListeners.class).values().forEach(super::register);
    }

    @Override
    public void onStart() {
        this.redisSubscribableStreamContainer.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        this.redisSubscribableStreamContainer.stop();
        super.onStop();
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.initChannelResolver());
    }

    @Override
    protected Stream bindStream(Channel channel, Listener listener) throws UnsupportedListenerException {
        channel.setName(this.expressionInterpreter.explain(channel.getName(), String.class));
        if (listener instanceof Consumer) {
            return new AsyncStreamDecorator(
                    new RedisConsumableStream(channel));
        }
        if (listener instanceof Subscriber) {
            RedisSubscribableStream stream = new RedisSubscribableStream(channel);
            this.redisSubscribableStreamContainer.add(stream);
            return stream;
        }
        throw new UnsupportedListenerException(listener);
    }

    @Override
    public void post(Message message) throws UnsupportedMessageException {
        if (message instanceof SubscribableMessage) {
            this.publishCommand.execute(message.getChannel(), message.getPayload());
            return;
        }
        if (message instanceof ConsumableMessage) {
            this.produceCommand.execute(message.getChannel(), message.getPayload());
            return;
        }
        throw new UnsupportedMessageException(message);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.onCreate();
    }

    private class ProduceCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = RedisMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            RedisMessageBus.this.redisTemplate.opsForList()
                    .rightPush(channel.getName(), RedisMessageBus.this.getSerializer().serialize(message));
        }
    }

    private class PublishCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = RedisMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            RedisMessageBus.this.redisTemplate.convertAndSend(channel.getName(), RedisMessageBus.this.getSerializer().serialize(message));
        }
    }

    private class RedisConsumableStream extends RemoteStream {

        public RedisConsumableStream(Channel channel) {
            super(channel, RedisMessageBus.this);
            this.onCreate();
        }

        @Override
        protected void onStart() {
            new LoopRunner().run(
                    () -> {
                        byte[] message = RedisMessageBus.this.redisTemplate.opsForList()
                                .leftPop(super.getChannel().getName(), 3000, TimeUnit.MILLISECONDS);
                        if (message != null) {
                            super.notifyAll(message);
                        }
                    },
                    exception -> {
                        try {
                            handleException(exception, null);
                        } catch (Exception error) {
                            throw new UncheckedAlohaException(error);
                        }
                    });
        }
    }

    private class RedisSubscribableStreamContainer {

        private RedisMessageListenerContainer redisMessageListenerContainer;

        public RedisSubscribableStreamContainer() {
            this.redisMessageListenerContainer = new RedisMessageListenerContainer();
            this.redisMessageListenerContainer.setConnectionFactory(RedisMessageBus.this.redisConnectionFactory);
            this.redisMessageListenerContainer.afterPropertiesSet();
        }

        public void add(RedisSubscribableStream stream) {
            MessageListenerAdapter adapter = new MessageListenerAdapter(stream);
            this.redisMessageListenerContainer.addMessageListener(adapter, new ChannelTopic(stream.getChannel().getName()));
        }

        public void start() {
            this.redisMessageListenerContainer.start();
        }

        public void stop() {
            this.redisMessageListenerContainer.stop();
        }
    }

    private class RedisSubscribableStream extends RemoteStream implements MessageListener {

        public RedisSubscribableStream(Channel channel) {
            super(channel, RedisMessageBus.this);
        }

        @Override
        public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
            super.notifyAll(message.getBody());
        }
    }
}
