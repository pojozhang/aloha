package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.Channel;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisProduceCommand extends RedisCommand {

    public RedisProduceCommand(RedisTemplate<String, String> redisTemplate,
        RedisMessageBus messageBus) {
        super(redisTemplate, messageBus);
    }

    @Override
    public void execute(Channel channel, Object message) {
        this.redisTemplate.opsForList()
            .rightPush(channel.getName(), (String) messageBus.getSerializer().serialize(message));
    }
}
