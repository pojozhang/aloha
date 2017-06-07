package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.ext.spring.data.redis.annotation.RedisSubscriber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringRunner.class)
public class RedisEventBusTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Subscriber subscriber;
    private EventBus eventBus;

    @Before
    public void setUp() {
        this.eventBus = new RedisEventBus(this.applicationContext);
        this.eventBus.start();
    }

    @Test
    public void the_subscriber_should_be_invoked_after_an_event_is_post() throws InterruptedException {
        this.eventBus.post(new Event("test"));

        Thread.sleep(100000);
        //spy(this.subscriber).onEvent(any(Event.class));
    }

    @RedisSubscriber
    public static class Subscriber {

        @Subscribe
        public void onEvent(Event event) {
            throw new RuntimeException("1111");
        }

        @Subscribe
        public void onEvent2(Event event) {
            throw new RuntimeException("2222");
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