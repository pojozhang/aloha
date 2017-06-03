package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public interface EventBus extends SubscriberRegistry {

    void post(Object event);

    void post(String channel, Object event);

    void start();

    void shutdown();

    Serializer getSerializer();

    Deserializer getDeserializer();
}
