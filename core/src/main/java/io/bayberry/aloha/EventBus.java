package io.bayberry.aloha;

public interface EventBus extends SubscriberRegistry {

    void post(Object event);

    void post(String channel, Object event);

    void start();

    void shutdown();
}
