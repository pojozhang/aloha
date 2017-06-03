package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.AbstractContinuedMultiChannelEventListener;
import io.bayberry.aloha.Subscriber;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisEventListener extends AbstractContinuedMultiChannelEventListener {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisEventListener(final RedisTemplate<String, String> redisTemplate, final String channel, final List<Subscriber> subscribers) {
        super(channel, subscribers);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void run() {
        String message = redisTemplate.opsForList().leftPop(super.channel, 0, TimeUnit.MILLISECONDS);
        this.notify(this.subscribers, message);
    }

    @Override
    public void notify(List<Subscriber> subscribers, Object message) {
        subscribers.forEach(subscriber -> {
            try {
                subscriber.invoke(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
