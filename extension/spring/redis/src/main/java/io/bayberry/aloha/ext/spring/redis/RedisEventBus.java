package io.bayberry.aloha.ext.spring.redis;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.AbstractEventInvoker;
import io.bayberry.aloha.MultiChannelEventBus;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisSubscriber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisEventBus extends SpringMultiChannelEventBus implements MultiChannelEventBus {

    private RedisTemplate<String, String> redisTemplate;
    private ExecutorService pool;

    public RedisEventBus(ApplicationContext applicationContext) {
        super(applicationContext);
        this.init();
    }

    private void init() {
        this.applicationContext.getBeansWithAnnotation(RedisSubscriber.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
    }

    @Override
    public void post(Object event) {
        this.post(this.resolveChannel(event.getClass()), event);
    }

    @Override
    public void post(String channel, Object event) {
        this.redisTemplate.opsForList().rightPush(channel, JSON.toJSONString(event));
    }

    @Override
    public void start() {
        Set<String> channels = super.channelInvocationsMapping.keySet();
        this.pool = Executors.newFixedThreadPool(channels.size());
        channels.forEach(channel -> pool.execute(new RedisEventRunner(channel)));
    }

    @Override
    public void shutdown() {
        this.pool.shutdown();
    }

    @Override
    public String resolveChannel(Class eventType) {
        return "event:" + eventType.getSimpleName();
    }

    @AllArgsConstructor
    private class RedisEventRunner extends AbstractEventInvoker implements Runnable {

        private String channel;

        @Override
        public void run() {
            while (true) {
                String message = redisTemplate.opsForList().leftPop(channel, 0, TimeUnit.MILLISECONDS);
                this.invoke(RedisEventBus.this.channelInvocationsMapping.get(channel), message);
            }
        }
    }
}
