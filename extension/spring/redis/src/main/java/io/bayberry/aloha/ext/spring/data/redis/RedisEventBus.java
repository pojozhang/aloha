package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.RemoteSpringEventBus;
import io.bayberry.aloha.ext.spring.data.redis.annotation.RedisSubscriber;
import io.bayberry.aloha.support.AsyncListenerDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisEventBus extends RemoteSpringEventBus {

    private static final RedisEventBusOptions DEFAULT_SETTINGS = new RedisEventBusOptions("event:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisEventBusOptions options;

    public RedisEventBus(ApplicationContext applicationContext) {
        this(applicationContext, DEFAULT_SETTINGS);
    }

    public RedisEventBus(ApplicationContext applicationContext, RedisEventBusOptions options) {
        super(applicationContext);
        this.options = options;
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
    public void post(Channel channel, Object event) {
        this.redisTemplate.opsForList().rightPush(channel.getName(), (String) getSerializer().serialize(event));
    }
}
