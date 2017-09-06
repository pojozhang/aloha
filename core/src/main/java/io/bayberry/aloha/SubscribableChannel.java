package io.bayberry.aloha;

public interface SubscribableChannel {

    void publish(Object message);

    void publish(Channel channel, Object message);
}
