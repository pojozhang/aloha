package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.ChannelResolver;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerRegistry;
import io.bayberry.aloha.SubscriberRegistry;
import io.bayberry.aloha.ext.spring.SpringThreadPoolEventBus;
import io.bayberry.aloha.ext.spring.data.redis.annotation.RedisSubscriber;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisEventBus extends SpringThreadPoolEventBus {

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
    public void post(String channel, Object event) {
        this.redisTemplate.opsForList().rightPush(channel, (String) getSerializer().serialize(event));
    }

    @Override
    protected SubscriberRegistry subscriberRegistry() {
        return null;
    }

    @Override
    protected ListenerRegistry listenerRegistry() {
        return null;
    }

    @Override
    protected ChannelResolver channelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.channelResolver());
    }

    @Override
    protected Listener listener(String channel) {
        return new RedisListener(channel, redisTemplate);
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        super.onStart();
    }
}
