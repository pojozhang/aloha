package io.bayberry.aloha;

public abstract class AbstractEventBus implements EventBus, LifeCycle {

    protected SubscriberRegistry subscriberRegistry;

    protected AbstractEventBus() {
        this.subscriberRegistry = newRegistryInstance();
        this.onCreate();
    }

    public SubscriberRegistry getSubscriberRegistry() {
        return subscriberRegistry;
    }

    @Override
    public final void register(Object subscriber) {
        this.subscriberRegistry.register(subscriber);
    }

    @Override
    public final void unregister(Object subscriber) {
        this.subscriberRegistry.unregister(subscriber);
    }

    protected abstract SubscriberRegistry newRegistryInstance();

    @Override
    public final void start() {
        this.onStart();
    }

    @Override
    public final void shutdown() {
        this.onDestroy();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
    }
}
