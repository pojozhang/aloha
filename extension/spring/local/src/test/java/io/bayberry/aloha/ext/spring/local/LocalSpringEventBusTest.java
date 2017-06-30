package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.test.spring.AsyncEventSpringTestCase;
import io.bayberry.aloha.test.spring.SyncEventSpringTestCase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalSpringEventBusTest {

    public static class SyncCase extends SyncEventSpringTestCase {

        @Override
        protected EventBus initEventBus() {
            return new LocalSpringEventBus(this.applicationContext);
        }
    }

    public static class AsyncCase extends AsyncEventSpringTestCase {

        @Override
        protected EventBus initEventBus() {
            return new LocalSpringEventBus(this.applicationContext);
        }
    }
}