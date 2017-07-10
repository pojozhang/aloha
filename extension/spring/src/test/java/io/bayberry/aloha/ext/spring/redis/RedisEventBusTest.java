package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.test.spring.AsyncEventSpringTestCase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisEventBusTest {

    public static class AsyncCase extends AsyncEventSpringTestCase {

        @Override
        protected EventBus initEventBus() {
            return new RedisEventBus(this.applicationContext);
        }
    }
}