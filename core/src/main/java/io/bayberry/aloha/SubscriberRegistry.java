package io.bayberry.aloha;

public interface SubscriberRegistry {

    void register(Object subscriber);

    void unregister(Object subscriber);
}
