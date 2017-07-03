package io.bayberry.aloha.test;

import io.bayberry.aloha.EventBus;
import org.junit.After;
import org.junit.Before;

public abstract class BaseTest {

    protected EventBus eventBus;
    protected TestSubscriber subscriber;

    protected abstract EventBus initEventBus();

    @Before
    public void setUp() {
        this.subscriber = new TestSubscriber();
        this.eventBus = initEventBus();
        this.eventBus.register(subscriber);
        this.eventBus.start();
    }

    @After
    public void tearDown() {
        this.eventBus.shutdown();
    }
}
