package io.bayberry.aloha.test.cases;

import static org.junit.Assert.assertEquals;

import io.bayberry.aloha.test.BaseTest;
import io.bayberry.aloha.test.SubSyncMessage;
import io.bayberry.aloha.test.SyncMessage;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;

public abstract class SyncMessageTestCase extends BaseTest {

    @Test
    public void the_subscriber_should_be_called_synchronously_after_single_message_is_post() {
        this.subscriber.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new SyncMessage());
        assertEquals(0, subscriber.countDownLatch.getCount());
    }

    @Test
    public void the_subscriber_should_be_called_synchronously_for_n_times_after_n_messages_are_post() {
        final int MESSAGE_NUMBER = 5;
        this.subscriber.countDownLatch = new CountDownLatch(MESSAGE_NUMBER);
        for (int i = 0; i < MESSAGE_NUMBER; i++) {
            this.messageBus.post(new SyncMessage());
        }
        assertEquals(0, subscriber.countDownLatch.getCount());
    }

    @Test
    public void the_subscriber_should_be_called_synchronously_if_target_message_is_base_type_of_source_message() {
        this.subscriber.countDownLatch = new CountDownLatch(1);
        this.messageBus.post(new SubSyncMessage());
        assertEquals(0, subscriber.countDownLatch.getCount());
    }
}
