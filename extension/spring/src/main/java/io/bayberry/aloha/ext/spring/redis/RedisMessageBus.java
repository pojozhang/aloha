package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.RemoteSpringMessageBus;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisSubscriber;
import io.bayberry.aloha.support.AsyncListenerDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMessageBus extends RemoteSpringMessageBus {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisMessageBusOptions options;

    public RedisMessageBus(ApplicationContext applicationContext) {
        this(applicationContext, DEFAULT_SETTINGS);
    }

    public RedisMessageBus(ApplicationContext applicationContext, RedisMessageBusOptions options) {
        super(applicationContext);
        this.options = options;
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        super.onStart();
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.initChannelResolver());
    }

    @Override
    public Listener bindListener(Channel channel) {
        return new AsyncListenerDecorator(new RedisListener(channel, redisTemplate, this));
    }

    @Override
    public void post(Channel channel, Object message) {
        this.redisTemplate.opsForList().rightPush(channel.getName(), (String) getSerializer().serialize(message));
    }
}
