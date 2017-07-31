package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.Command;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class RedisCommand implements Command {

    protected RedisTemplate<String, String> redisTemplate;
    protected RedisMessageBus messageBus;

    public RedisCommand(RedisTemplate<String, String> redisTemplate,
        RedisMessageBus messageBus) {
        this.redisTemplate = redisTemplate;
        this.messageBus = messageBus;
    }
}
