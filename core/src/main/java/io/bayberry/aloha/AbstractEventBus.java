package io.bayberry.aloha;

public abstract class AbstractEventBus implements EventBus {

    protected abstract SubscriberRegistry registry();

    @Override
    public final void register(Object subscriber) {
        registry().register(subscriber);
    }

    @Override
    public final void unregister(Object subscriber) {
        registry().unregister(subscriber);
    }
}
