package io.bayberry.aloha.event;

public interface EventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void listen();

    String getChannel(Class eventType);
}
