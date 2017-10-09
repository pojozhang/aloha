package io.bayberry.aloha.spring.redis;

import static org.awaitility.Awaitility.await;

import io.bayberry.aloha.ConsumableMessage;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.SubscribableMessage;
import io.bayberry.aloha.annotation.Concurrency;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.spring.redis.annotation.RedisListeners;
import java.util.concurrent.CountDownLatch;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringRunner.class)
public class RedisMessageBusTest {

    private static CountDownLatch countDownLatch;
    @Autowired
    private MessageBus messageBus;

    @Test
    public void the_consumer_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new ConsumableMessage(new SyncRedisMessage()));
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

    @Test
    public void the_consumer_should_be_called_asynchronously_for_n_times_after_n_messages_are_post() {
        final int NUMBER = 6;
        this.countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            this.messageBus.post(new ConsumableMessage(new AsyncRedisMessage()));
        }
        await().atLeast(Duration.ONE_SECOND).atMost(Duration.FIVE_SECONDS)
                .until(() -> this.countDownLatch.getCount() == 0);
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new SubscribableMessage(new SyncRedisMessage()));
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

    @RedisListeners
    public static class TestRedisListeners {

        @Consume
        public void onConsume(SyncRedisMessage message) {
            countDownLatch.countDown();
        }

        @Concurrency(threads = 3, capacity = 3)
        @Consume
        public void onConsume(AsyncRedisMessage message) throws InterruptedException {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }

        @Subscribe
        public void onSubscribe(SyncRedisMessage message) {
            countDownLatch.countDown();
        }
    }

    public static class SyncRedisMessage {

    }

    public static class AsyncRedisMessage {

    }
}