package io.bayberry.aloha;

public interface Producer {

    void produce(Object message);

    void produce(Channel channel, Object message);
}
