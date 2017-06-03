package io.bayberry.aloha.ext.spring.redis;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.ext.spring.SpringMultiChannelEventBus;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisSubscriber;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class RedisEventBus extends SpringMultiChannelEventBus {

    private static final RedisEventBusSettings DEFAULT_SETTINGS = new RedisEventBusSettingsBuilder().channelPrefix("event:").build();
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
    public void post(Object event) {
        this.post(this.resolveChannel(event.getClass()), event);
    }

    @Override
    public void post(String channel, Object event) {
        this.redisTemplate.opsForList().rightPush(channel, JSON.toJSONString(event));
    }

    @Override
    public String resolveChannel(Class eventType) {
        return this.settings.getChannelPrefix() + eventType.getSimpleName();
    }

    @Override
    public RedisEventListener getEventListener(String channel, List<Subscriber> subscribers) {
        return new RedisEventListener(redisTemplate, channel, subscribers);
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        super.onStart();
    }
}
