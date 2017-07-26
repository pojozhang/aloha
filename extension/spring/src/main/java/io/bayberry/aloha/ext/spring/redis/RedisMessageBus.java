package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.ext.spring.SpringListenerResolver;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.support.AsyncListenerDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMessageBus extends RemoteMessageBus {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisMessageBusOptions options;
    private ApplicationContext applicationContext;

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
        super.onStart();
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.initChannelResolver());
    }

    @Override
    public Receiver bindListener(Channel channel) {
        return new AsyncListenerDecorator(
                new RedisReceiver(channel, redisTemplate, this));
    }

    @Override
    public void produce(Channel channel, Object message) {
        this.redisTemplate.opsForList().rightPush(channel.getName(), (String) getSerializer().serialize(message));
    }

    @Override
    public void publish(Channel channel, Object message) {
        this.redisTemplate.convertAndSend(channel.getName(), getSerializer().serialize(message));
    }

    @Override
    public ListenerResolver initListenerResolver() {
        return new SpringListenerResolver(applicationContext);
    }
}
