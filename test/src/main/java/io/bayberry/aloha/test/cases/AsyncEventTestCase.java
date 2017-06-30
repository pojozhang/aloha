package io.bayberry.aloha.test.cases;

import static org.junit.Assert.assertEquals;

import io.bayberry.aloha.test.BaseTest;
import io.bayberry.aloha.test.SyncEvent;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;

public abstract class AsyncEventTestCase extends BaseTest {

    @Test
    public void the_subscriber_should_be_called_synchronously_after_single_event_is_post() {
        this.subscriber.countDownLatch = new CountDownLatch(1);
        this.eventBus.post(new SyncEvent());
        assertEquals(0, subscriber.countDownLatch.getCount());
    }

    @Test
    public void the_subscriber_should_be_called_synchronously_for_n_times_after_n_events_are_post() {
        final int NUMBER = 5;
        this.subscriber.countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            this.eventBus.post(new SyncEvent());
        }
        assertEquals(0, subscriber.countDownLatch.getCount());
    }
}
