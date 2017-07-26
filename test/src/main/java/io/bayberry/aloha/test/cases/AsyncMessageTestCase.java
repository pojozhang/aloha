package io.bayberry.aloha.test.cases;

import static org.awaitility.Awaitility.await;

import io.bayberry.aloha.test.AsyncMessage;
import io.bayberry.aloha.test.BaseTest;
import java.util.concurrent.CountDownLatch;
import org.awaitility.Duration;
import org.junit.Test;

public abstract class AsyncMessageTestCase extends BaseTest {

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.subscriber.countDownLatch = new CountDownLatch(1);
        this.messageBus.produce(new AsyncMessage());
        await().atMost(Duration.TWO_SECONDS).until(() -> subscriber.countDownLatch.getCount() == 0);
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_for_n_times_after_n_messages_are_post() {
        final int NUMBER = 6;
        this.subscriber.countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            this.messageBus.produce(new AsyncMessage());
        }
        await().atLeast(Duration.ONE_SECOND).atMost(Duration.FIVE_SECONDS)
            .until(() -> subscriber.countDownLatch.getCount() == 0);
    }
}
