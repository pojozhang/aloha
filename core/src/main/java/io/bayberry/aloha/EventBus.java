package io.bayberry.aloha;

public interface EventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void listen();
}
