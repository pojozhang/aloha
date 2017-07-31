package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.ext.spring.BaseSpringTest;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class LocalSpringMessageBusTest extends BaseSpringTest {

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

//    @Test
//    public void the_subscriber_should_be_called_synchronously_after_single_message_is_post() {
//        this.subscriber.countDownLatch = new CountDownLatch(1);
//        this.messageBus.produce(new SyncMessage());
//        assertEquals(0, subscriber.countDownLatch.getCount());
//    }
//
//    @Test
//    public void the_subscriber_should_be_called_synchronously_for_n_times_after_n_messages_are_post() {
//        final int MESSAGE_NUMBER = 5;
//        this.subscriber.countDownLatch = new CountDownLatch(MESSAGE_NUMBER);
//        for (int i = 0; i < MESSAGE_NUMBER; i++) {
//            this.messageBus.produce(new SyncMessage());
//        }
//        assertEquals(0, subscriber.countDownLatch.getCount());
//    }
//
//    @Test
//    public void the_subscriber_should_be_called_synchronously_if_target_message_is_base_type_of_source_message() {
//        this.subscriber.countDownLatch = new CountDownLatch(1);
//        this.messageBus.produce(new SubSyncMessage());
//        assertEquals(0, subscriber.countDownLatch.getCount());
//    }
}