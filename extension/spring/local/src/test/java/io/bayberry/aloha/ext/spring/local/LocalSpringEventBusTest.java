package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.ext.spring.local.annotation.SpringSubscriber;
import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringRunner.class)
public class LocalSpringEventBusTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Subscriber subscriber;
    private EventBus eventBus;

    @Before
    public void setUp() {
        this.eventBus = new LocalSpringEventBus(this.applicationContext);
        this.eventBus.start();
    }

    @Test
    public void the_subscriber_should_be_invoked_after_an_event_is_post() {
        for (int i = 0; i < 6; i++) {
            new Thread(()->this.eventBus.post(new Event("name" + 3))).start();
        }

        await().atMost(Duration.FIVE_SECONDS).until(() -> subscriber.countDownLatch.getCount() == 0);
    }

    @SpringSubscriber
    public static class Subscriber {

        public CountDownLatch countDownLatch = new CountDownLatch(12);
        private Logger log = LoggerFactory.getLogger(Subscriber.class);

        @Subscribe(threads = 0)
        public void onEvent1(Event event) throws InterruptedException {
            log.info("onEvent1");
            countDownLatch.countDown();
            if (event.getName().equals("name1")) {
                throw new RuntimeException();
            }
            Thread.sleep(10000);

        }

        @Subscribe
        public void onEvent2(Event event) {
            log.info("onEvent2");
            countDownLatch.countDown();
            if (event.getName().equals("name1")) {
                throw new RuntimeException();
            }
        }
    }

    public static class Event {

        private String name;

        private Event() {
        }

        public Event(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}