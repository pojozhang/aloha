package io.bayberry.aloha.ext.spring.data.redis;

import io.bayberry.aloha.test.TestSubscriber;
import io.bayberry.aloha.test.spring.SyncEventSpringTestCase;
import org.junit.Before;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisEventBusTest extends SyncEventSpringTestCase {

    @Before
    public void setUp() {
        this.subscriber = new TestSubscriber();
        this.eventBus = new RedisEventBus(this.applicationContext);
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }
}