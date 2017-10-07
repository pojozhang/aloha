package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.ConsumableMessage;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.SubscribableMessage;
import io.bayberry.aloha.annotation.Concurrency;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.spring.rabbit.annotation.RabbitListeners;
import org.awaitility.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringRunner.class)
public class RabbitMessageBusTest {

    private static CountDownLatch countDownLatch;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ConnectionFactory connectionFactory;
    private MessageBus messageBus;

    @Before
    public void setUp() {
        messageBus = new RabbitMessageBus(applicationContext, connectionFactory);
        messageBus.start();
    }

    @After
    public void tearDown() {
        messageBus.stop();
    }

    @Ignore
    @Test
    public void the_consumer_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new ConsumableMessage(new SyncRabbitMessage()));
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

    @Ignore
    @Test
    public void the_consumer_should_be_called_asynchronously_for_n_times_after_n_messages_are_post() {
        final int NUMBER = 6;
        this.countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            this.messageBus.post(new ConsumableMessage(new AsyncRabbitMessage()));
        }
        await().atLeast(Duration.ONE_SECOND).atMost(Duration.FIVE_SECONDS)
                .until(() -> this.countDownLatch.getCount() == 0);
    }

    @Ignore
    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new SubscribableMessage(new SyncRabbitMessage()));
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

    @RabbitListeners
    public static class TestRedisListeners {

        @Consume
        public void onConsume(SyncRabbitMessage message) {
            countDownLatch.countDown();
        }

        @Concurrency(maxCount = 3, capacity = 3)
        @Consume
        public void onConsume(AsyncRabbitMessage message) throws InterruptedException {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }

        @Subscribe
        public void onSubscribe(SyncRabbitMessage message) {
            countDownLatch.countDown();
        }
    }

    public static class SyncRabbitMessage {

    }

    public static class AsyncRabbitMessage {

    }
}