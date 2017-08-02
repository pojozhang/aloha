package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.spring.BaseSpringTest;
import io.bayberry.aloha.spring.local.annotation.SpringEventListeners;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

@SpringBootApplication
public class LocalSpringMessageBusTest extends BaseSpringTest {

    private static CountDownLatch countDownLatch;
    @Autowired
    private ApplicationContext applicationContext;
    private LocalSpringMessageBus localSpringMessageBus;

    @Before
    public void setUp() {
        localSpringMessageBus = new LocalSpringMessageBus(applicationContext);
        localSpringMessageBus.start();
    }

    @After
    public void tearDown() {
        localSpringMessageBus.stop();
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.localSpringMessageBus.publish(new SyncSpringMessage());
        assertEquals(0, this.countDownLatch.getCount());
    }

    @Test
    public void the_subscriber_should_be_called_synchronously_for_n_times_after_n_messages_are_post() {
        final int MESSAGE_NUMBER = 5;
        this.countDownLatch = new CountDownLatch(MESSAGE_NUMBER);
        for (int i = 0; i < MESSAGE_NUMBER; i++) {
            this.localSpringMessageBus.publish(new SyncSpringMessage());
        }
        assertEquals(0, this.countDownLatch.getCount());
    }

    @Test
    public void the_subscriber_should_be_called_synchronously_if_target_message_is_base_type_of_source_message() {
        this.countDownLatch = new CountDownLatch(1);
        this.localSpringMessageBus.publish(new ChildSyncSpringMessage());
        assertEquals(0, this.countDownLatch.getCount());
    }

    @SpringEventListeners
    public static class SpringEventConsumers {

        @Consume
        public void onReceive(SyncSpringMessage message) {
            countDownLatch.countDown();
        }

        @Executor(maxCount = 3, capacity = 3)
        @Consume
        public void onReceive(AsyncSpringMessage message) throws InterruptedException {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }
    }

    public static class SyncSpringMessage {

    }

    public static class ChildSyncSpringMessage extends SyncSpringMessage {

    }

    public static class AsyncSpringMessage {

    }
}