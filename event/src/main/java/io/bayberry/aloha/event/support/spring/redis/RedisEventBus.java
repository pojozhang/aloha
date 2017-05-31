package io.bayberry.aloha.event.support.spring.redis;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.event.AbstractEventInvoker;
import io.bayberry.aloha.event.MultiChannelEventBus;
import io.bayberry.aloha.event.support.spring.SpringIntegratedEventBus;
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
public class RedisEventBus extends SpringIntegratedEventBus implements MultiChannelEventBus {

    private RedisTemplate<String, String> redisTemplate;

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
        this.post(this.getChannel(event.getClass()), event);
    }

    @Override
    public void post(String channel, Object event) {
        this.redisTemplate.opsForList().rightPush(channel, JSON.toJSONString(event));
    }

    @Override
    public void listen() {
        Set<String> channels = super.channelInvocationsMapping.keySet();
        ExecutorService pool = Executors.newFixedThreadPool(channels.size());
        channels.forEach(channel -> pool.execute(new RedisEventRunner(channel)));
    }

    @Override
    public String getChannel(Class eventType) {
        return "event:" + eventType.getSimpleName();
    }

    @AllArgsConstructor
    private class RedisEventRunner extends AbstractEventInvoker {

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
