package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.ext.spring.SpringChannelResolver;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.support.AsyncListenerDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMessageBus extends RemoteMessageBus implements Publisher, Producer {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisMessageBusOptions options;
    private ApplicationContext applicationContext;
    private RedisProduceCommand redisProduceCommand;
    private RedisPublishCommand redisPublishCommand;

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
        this.redisProduceCommand = new RedisProduceCommand(this.redisTemplate, this);
        this.redisPublishCommand = new RedisPublishCommand(this.redisTemplate, this);
        super.onStart();
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(),
            new SpringChannelResolver(applicationContext));
    }

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return new AsyncListenerDecorator(
            new RedisReceiver(listener.getChannel(), redisTemplate, this));
    }

    @Override
    public void produce(Object message) {
        this.produce(this.getChannelResolver().resolve(message.getClass()), message);
    }

    @Override
    public void produce(Channel channel, Object message) {
        this.post(this.redisProduceCommand, channel, message);
    }

    @Override
    public void publish(Object message) {
        this.publish(this.getChannelResolver().resolve(message.getClass()), message);
    }

    @Override
    public void publish(Channel channel, Object message) {
        this.post(this.redisPublishCommand, channel, message);
    }
}
