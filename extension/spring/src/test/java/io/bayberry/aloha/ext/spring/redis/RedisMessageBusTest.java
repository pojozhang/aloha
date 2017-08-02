package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.ext.spring.BaseSpringTest;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisListeners;
import org.awaitility.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;

@SpringBootApplication
public class RedisMessageBusTest extends BaseSpringTest {

    private static CountDownLatch countDownLatch;
    @Autowired
    private ApplicationContext applicationContext;
    private RedisMessageBus redisMessageBus;

    @Before
    public void setUp() {
        redisMessageBus = new RedisMessageBus(applicationContext);
        redisMessageBus.start();
    }

    @After
    public void tearDown() {
        redisMessageBus.stop();
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.redisMessageBus.produce(new RedisMessage());
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

//    @Test
//    public void the_subscriber_should_be_called_asynchronously_for_n_times_after_n_messages_are_post() {
//        final int NUMBER = 6;
//        this.countDownLatch = new CountDownLatch(NUMBER);
//        for (int i = 0; i < NUMBER; i++) {
//            this.redisMessageBus.produce(new AsyncMessage());
//        }
//        await().atLeast(Duration.ONE_SECOND).atMost(Duration.FIVE_SECONDS)
//                .until(() -> this.countDownLatch.getCount() == 0);
//    }

    @RedisListeners
    public static class RedisConsumers {

        @Consume
        public void onReceive(RedisMessage message) {
            countDownLatch.countDown();
        }
    }

    public static class RedisMessage {

    }
}