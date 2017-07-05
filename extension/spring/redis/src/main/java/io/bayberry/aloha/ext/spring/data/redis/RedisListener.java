package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.RemoteEventBus;
import io.bayberry.aloha.RemoteListener;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.LoopRunner;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisListener extends RemoteListener {

    private RedisTemplate<String, String> redisTemplate;

    public RedisListener(Channel channel, RedisTemplate<String, String> redisTemplate, RemoteEventBus eventBus) {
        super(channel, eventBus);
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
