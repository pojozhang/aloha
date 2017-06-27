package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.test.Event;
import io.bayberry.aloha.test.Subscriber;
import io.bayberry.aloha.test.spring.BaseSpringTest;
import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.awaitility.Awaitility.await;

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
    public void the_subscriber_should_be_invoked_after_an_event_is_post() {
        for (int i = 0; i < 6; i++) {
            this.eventBus.post(new Event("name" + 3));
        }

        await().atMost(Duration.FIVE_SECONDS).until(() -> subscriber.countDownLatch.getCount() == 0);
    }
}