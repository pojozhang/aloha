package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.MultiChannelSubscriberInvoker;
import io.bayberry.aloha.MultiChannelSubscriberRegistry;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisSubscriberInvoker extends MultiChannelSubscriberInvoker {

    private final RedisTemplate<String, String> redisTemplate;
    private final MultiChannelSubscriberRegistry registry;

    public RedisSubscriberInvoker(String channel, RedisTemplate<String, String> redisTemplate, MultiChannelSubscriberRegistry registry) {
        super(channel);
        this.redisTemplate = redisTemplate;
        this.registry = registry;
    }

    @Override
    public void run() {
        while (true) {
            String message = redisTemplate.opsForList().leftPop(channel, 0, TimeUnit.MILLISECONDS);
            this.invoke(this.registry.getInvocations(channel), message);
        }
    }
}
