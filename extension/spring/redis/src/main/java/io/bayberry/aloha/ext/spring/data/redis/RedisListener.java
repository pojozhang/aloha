package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.RemoteEventBus;
import io.bayberry.aloha.RemoteListener;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisListener extends RemoteListener {

    private RedisTemplate<String, String> redisTemplate;

    public RedisListener(String channel, RedisTemplate<String, String> redisTemplate, RemoteEventBus eventBus) {
        super(channel, eventBus);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void onStart() {
        new LoopRunner().run(
                () -> super.notifyAll(redisTemplate.opsForList().leftPop(super.getChannel(), 0, TimeUnit.MILLISECONDS)),
                exception -> {
                    try {
                        handleException(exception, null);
                    } catch (Exception error) {
                        throw new AlohaException(error);
                    }
                });
    }
}
