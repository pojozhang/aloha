package io.bayberry.aloha.test.cases;

import static org.awaitility.Awaitility.await;

import io.bayberry.aloha.test.AsyncEvent;
import io.bayberry.aloha.test.BaseTest;
import java.util.concurrent.CountDownLatch;
import org.awaitility.Duration;
import org.junit.Test;

public abstract class AsyncEventTestCase extends BaseTest {

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_event_is_post() {
        this.subscriber.countDownLatch = new CountDownLatch(1);
        this.eventBus.post(new AsyncEvent());
        await().atMost(Duration.TWO_SECONDS).until(() -> subscriber.countDownLatch.getCount() == 0);
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_for_n_times_after_n_events_are_post() {
        final int NUMBER = 6;
        this.subscriber.countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            this.eventBus.post(new AsyncEvent());
        }
        await().atLeast(Duration.ONE_SECOND).atMost(Duration.FIVE_SECONDS)
            .until(() -> subscriber.countDownLatch.getCount() == 0);
    }
}
