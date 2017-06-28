package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.test.Subscriber;
import io.bayberry.aloha.test.spring.BaseSpringTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LocalSpringEventBusTest extends BaseSpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        this.subscriber = new Subscriber();
        this.eventBus = new LocalSpringEventBus(this.applicationContext);
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }
}