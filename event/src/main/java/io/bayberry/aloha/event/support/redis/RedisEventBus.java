package io.bayberry.aloha.event.support.redis;

import io.bayberry.aloha.event.AbstractEventBus;
import io.bayberry.aloha.event.Event;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisEventBus extends AbstractEventBus {

    private final RedisTemplate<String, Event<?>> redisTemplate;
    private final RedisEventBusSettings settings;

    public RedisEventBus(RedisTemplate<String, Event<?>> redisTemplate, RedisEventBusSettings settings) {
        this.redisTemplate = redisTemplate;
        this.settings = settings;
    }

    @Override
    public void post(Event<?> event) {
        this.redisTemplate.convertAndSend(this.settings.getNamespace(), event);
    }
}
