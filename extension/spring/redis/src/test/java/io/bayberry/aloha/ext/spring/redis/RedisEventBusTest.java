package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.ext.spring.redis.annotation.RedisSubscriber;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    }

    @Test
    public void the_subscriber_should_be_invoked_after_an_event_is_post() {
        this.eventBus.post(new Event("test"));
        spy(this.subscriber).onEvent(any(Event.class));
    }

    @RedisSubscriber
    private static class Subscriber {

        @Subscribe
        public void onEvent(Event event) {
        }
    }

    @Getter
    @AllArgsConstructor
    private static class Event {

        private String name;
    }
}