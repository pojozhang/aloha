package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.test.Subscriber;
import io.bayberry.aloha.test.spring.BaseLocalSpringTest;
import org.junit.Before;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalSpringEventBusTest extends BaseLocalSpringTest {

    @Before
    public void setUp() {
        this.subscriber = new Subscriber();
        this.eventBus = new LocalSpringEventBus(this.applicationContext);
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }
}