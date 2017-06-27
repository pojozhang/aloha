package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.test.Subscriber;
import io.bayberry.aloha.test.SyncEvent;
import io.bayberry.aloha.test.spring.BaseSpringTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

@SpringBootApplication
public class LocalSpringEventBusTest extends BaseSpringTest {

    @Autowired
    private ApplicationContext applicationContext;
    private Subscriber subscriber;
    private EventBus eventBus;

    @Before
    public void setUp() {
        this.subscriber = new Subscriber();
        this.eventBus = new LocalSpringEventBus(this.applicationContext);
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }

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