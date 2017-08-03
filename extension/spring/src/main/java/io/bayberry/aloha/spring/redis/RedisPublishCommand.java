package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.Channel;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisPublishCommand extends RedisCommand {

    public RedisPublishCommand(RedisTemplate<String, String> redisTemplate,
        RedisMessageBus messageBus) {
        super(redisTemplate, messageBus);
    }

    @Override
    public void execute(Channel channel, Object message) {
        this.redisTemplate.convertAndSend(channel.getName(), messageBus.getSerializer().serialize(message));
    }
}
