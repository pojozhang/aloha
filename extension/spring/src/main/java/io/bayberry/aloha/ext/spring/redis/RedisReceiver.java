package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.RemoteMessageBus;
import io.bayberry.aloha.RemoteReceiver;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisReceiver extends RemoteReceiver {

    private RedisTemplate<String, String> redisTemplate;

    public RedisReceiver(Channel channel, RedisTemplate<String, String> redisTemplate, RemoteMessageBus messageBus) {
        super(channel, messageBus);
        this.redisTemplate = redisTemplate;
        this.onCreate();
    }

    @Override
    protected void onStart() {
        new LoopRunner().run(
            () -> {
                String message = redisTemplate.opsForList()
                    .leftPop(super.getChannel().getName(), 3000, TimeUnit.MILLISECONDS);
                if (message != null) {
                    super.notifyAll(message);
                }
            },
            exception -> {
                try {
                    handleException(exception, null);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            });
    }
}
