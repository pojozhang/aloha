package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.*;

public class RabbitMessageBus extends RemoteMessageBus implements AsyncMessageBus {

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return null;
    }

    @Override
    public void produce(Object message) {

    }

    @Override
    public void produce(Channel channel, Object message) {

    }

    @Override
    public void publish(Object message) {

    }

    @Override
    public void publish(Channel channel, Object message) {

    }
}
