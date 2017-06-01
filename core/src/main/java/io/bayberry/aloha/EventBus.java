package io.bayberry.aloha;

public interface EventBus extends SubscriberRegistry {

    void post(Object event);

    void start();

    void shutdown();
}
