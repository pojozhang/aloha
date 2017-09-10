package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.NotSupportedMessageException;
import io.bayberry.aloha.spring.SpringListenerResolver;
import io.bayberry.aloha.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.support.AsyncReceiverDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMessageBus extends RemoteMessageBus {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisMessageBusOptions options;
    private ApplicationContext applicationContext;
    private ProduceCommand produceCommand;
    private PublishCommand publishCommand;

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
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        this.produceCommand = new ProduceCommand();
        this.publishCommand = new PublishCommand();
        super.onStart();
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
    protected Receiver bindReceiver(Listener listener) {
        return new AsyncReceiverDecorator(
                new RedisReceiver(listener.getChannel(), redisTemplate, this));
    }

    @Override
    public void post(Message message) {
        if (message instanceof SubscribableMessage) {
            this.publishCommand.execute(message.getChannel(), message.getPayload());
        } else if (message instanceof ConsumableMessage) {
            this.produceCommand.execute(message.getChannel(), message.getPayload());
        } else {
            throw new NotSupportedMessageException(message);
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

    public class PublishCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = RedisMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            RedisMessageBus.this.redisTemplate.convertAndSend(channel.getName(), RedisMessageBus.this.getSerializer().serialize(message));
        }
    }
}
