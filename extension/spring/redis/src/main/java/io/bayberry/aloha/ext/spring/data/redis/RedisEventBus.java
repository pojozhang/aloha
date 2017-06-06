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

    private static final RedisEventBusSettings DEFAULT_SETTINGS = new RedisEventBusSettingsBuilder()
        .channelPrefix("event:").build();
    private RedisTemplate<String, String> redisTemplate;
    private RedisEventBusSettings settings;

    public RedisEventBus(ApplicationContext applicationContext) {
        this(applicationContext, DEFAULT_SETTINGS);
    }

    public RedisEventBus(ApplicationContext applicationContext, RedisEventBusSettings settings) {
        super(applicationContext);
        this.settings = settings;
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
        return new PrefixChannelResolverDecorator(this.settings.getChannelPrefix(), super.channelResolver());
    }

    @Override
    protected Listener listener(String channel) {
        //TODO: return Redis Listener
        return null;
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        super.onStart();
    }
}
