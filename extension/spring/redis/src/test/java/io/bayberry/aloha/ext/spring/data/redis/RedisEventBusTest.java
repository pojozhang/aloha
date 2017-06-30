package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.test.Subscriber;
import io.bayberry.aloha.test.spring.RemoteSpringTestCase;
import org.junit.Before;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisEventBusTest extends RemoteSpringTestCase {

    @Before
    public void setUp() {
        this.subscriber = new Subscriber();
        this.eventBus = new RedisEventBus(this.applicationContext);
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }
}