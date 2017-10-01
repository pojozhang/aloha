package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.spring.SpringListenerResolver;
import io.bayberry.aloha.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.spring.util.SpringUtils;
import io.bayberry.aloha.support.AsyncStreamDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import io.bayberry.aloha.util.Assert;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.TimeUnit;

public class RedisMessageBus extends RemoteMessageBus {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private ApplicationContext applicationContext;
    private RedisTemplate<String, String> redisTemplate;
    private RedisConnectionFactory redisConnectionFactory;
    private RedisSubscribableStreamContainer redisSubscribableStreamContainer;
    private ProduceCommand produceCommand;
    private PublishCommand publishCommand;
    private RedisMessageBusOptions options;

    public RedisMessageBus(ApplicationContext applicationContext) {
        this(applicationContext, DEFAULT_SETTINGS);
    }

    public RedisMessageBus(ApplicationContext applicationContext, RedisMessageBusOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisListeners.class).values().forEach(super::register);
        this.redisConnectionFactory = Assert.notNull(SpringUtils.getBean(this.applicationContext, RedisConnectionFactory.class), "RedisConnectionFactory not found");
        this.redisTemplate = new RedisTemplate<>();
        this.redisTemplate.setConnectionFactory(this.redisConnectionFactory);
        this.redisTemplate.afterPropertiesSet();
        this.redisSubscribableStreamContainer = new RedisSubscribableStreamContainer();
        this.produceCommand = new ProduceCommand();
        this.publishCommand = new PublishCommand();
        super.onStart();
        this.redisSubscribableStreamContainer.start();
    }

    @Override
    protected ListenerResolver initListenerResolver() {
        return new SpringListenerResolver(this.applicationContext);
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.initChannelResolver());
    }

    @Override
    protected Stream bindStream(Listener listener) {
        if (listener instanceof Consumer) {
            return new AsyncStreamDecorator(
                    new RedisConsumableStream(listener.getChannel()));
        }
        if (listener instanceof Subscriber) {
            RedisSubscribableStream stream = new RedisSubscribableStream(listener.getChannel());
            this.redisSubscribableStreamContainer.add(stream);
            return stream;
        }
        return null;
    }

    @Override
    public void post(Message message) {
        if (message instanceof SubscribableMessage) {
            this.publishCommand.execute(message.getChannel(), message.getPayload());
        } else if (message instanceof ConsumableMessage) {
            this.produceCommand.execute(message.getChannel(), message.getPayload());
        } else {
            super.handleUnsupportedMessage(message);
        }
    }

    private class ProduceCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = RedisMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            RedisMessageBus.this.redisTemplate.opsForList()
                    .rightPush(channel.getName(), (String) RedisMessageBus.this.getSerializer().serialize(message));
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
                        String message = RedisMessageBus.this.redisTemplate.opsForList()
                                .leftPop(super.getChannel().getName(), 3000, TimeUnit.MILLISECONDS);
                        if (message != null) {
                            super.notifyAll(message);
                        }
                    },
                    exception -> {
                        try {
                            handleException(exception, null);
                        } catch (Exception error) {
                            throw new AlohaException(error);
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
