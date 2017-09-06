package io.bayberry.aloha;

public interface ConsumableChannel {

    void produce(Object message);

    void produce(Channel channel, Object message);
}
