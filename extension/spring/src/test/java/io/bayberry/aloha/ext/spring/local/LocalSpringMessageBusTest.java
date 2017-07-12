package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.test.spring.AsyncMessageSpringTestCase;
import io.bayberry.aloha.test.spring.SyncMessageSpringTestCase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalSpringMessageBusTest {

    public static class SyncCase extends SyncMessageSpringTestCase {

        @Override
        protected MessageBus initMessageBus() {
            return new LocalSpringMessageBus(this.applicationContext);
        }
    }

    public static class AsyncCase extends AsyncMessageSpringTestCase {

        @Override
        protected MessageBus initMessageBus() {
            return new LocalSpringMessageBus(this.applicationContext);
        }
    }
}