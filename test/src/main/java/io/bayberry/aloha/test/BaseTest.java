package io.bayberry.aloha.test;

import io.bayberry.aloha.MessageBus;
import org.junit.After;
import org.junit.Before;

public abstract class BaseTest {

    protected MessageBus messageBus;
    protected TestSubscriber subscriber;

    protected abstract MessageBus initMessageBus();

    @Before
    public void setUp() {
        this.subscriber = new TestSubscriber();
        this.messageBus = initMessageBus();
        this.messageBus.register(subscriber);
        this.messageBus.start();
    }

    @After
    public void tearDown() {
        this.messageBus.shutdown();
    }
}
