package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.support.AsyncListener;
import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.util.LoopRunner;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisListener extends AsyncListener {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisListener(final String channel, final RedisTemplate<String, String> redisTemplate,
        final EventBus eventBus) {
        super(channel, eventBus);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void listen() {
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
