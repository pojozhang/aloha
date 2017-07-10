package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;

import static org.junit.Assert.*;

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