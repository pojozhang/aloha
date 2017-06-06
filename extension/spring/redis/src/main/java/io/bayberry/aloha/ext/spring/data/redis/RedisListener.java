package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisListener extends Listener {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisListener(final String channel, final RedisTemplate<String, String> redisTemplate) {
        super(channel);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onStart() {
        new LoopRunner().run(() -> super.notifyAll(redisTemplate.opsForList().leftPop(super.getChannel(), 0, TimeUnit.MILLISECONDS)),
                exception -> {

                });
    }
}
