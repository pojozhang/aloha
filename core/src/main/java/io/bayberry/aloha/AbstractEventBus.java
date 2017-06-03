package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.JsonDeserializer;
import io.bayberry.aloha.transport.JsonSerializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class AbstractEventBus implements EventBus, LifeCycle {

    protected SubscriberRegistry subscriberRegistry;
    protected boolean isRunning;

    protected AbstractEventBus() {
        this.subscriberRegistry = newRegistryInstance();
        this.onCreate();
    }

    protected SubscriberRegistry getSubscriberRegistry() {
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
        if (this.isRunning) {
            throw new AlohaException("Event bus has already been started");
        }
        this.isRunning = true;
        this.onStart();
    }

    @Override
    public final void shutdown() {
        if (!this.isRunning) {
            throw new AlohaException("Event bus has not been started yet");
        }
        this.isRunning = false;
        this.onDestroy();
    }

    @Override
    public Serializer getSerializer() {
        return new JsonSerializer();
    }

    @Override
    public Deserializer getDeserializer() {
        return new JsonDeserializer();
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
