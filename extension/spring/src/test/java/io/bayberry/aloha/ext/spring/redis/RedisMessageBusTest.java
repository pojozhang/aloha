package io.bayberry.aloha.ext.spring.redis;

import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.test.spring.AsyncMessageSpringTestCase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisMessageBusTest {

    public static class AsyncCase extends AsyncMessageSpringTestCase {

        @Override
        protected MessageBus initMessageBus() {
            return new RedisMessageBus(this.applicationContext);
        }
    }
}