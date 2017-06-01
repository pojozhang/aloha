package io.bayberry.aloha.ext.spring.redis;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.MultiChannelSubscriberInvoker;
import io.bayberry.aloha.ext.spring.SpringMultiChannelEventBus;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
public class RedisEventBus extends SpringMultiChannelEventBus {

    private static final RedisEventBusSettings DEFAULT_SETTINGS = new RedisEventBusSettings("event:");
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
    public void onCreate() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
    }

    @Override
    protected MultiChannelSubscriberInvoker getSubscriberInvoker(String channel) {
        return new RedisSubscriberInvoker(channel, redisTemplate, this.registry());
    }
}
