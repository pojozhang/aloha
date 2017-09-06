package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.*;

public class RabbitMessageBus extends RemoteMessageBus implements SubscribableChannel, ConsumableChannel {

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

    @Override
    public <T> T proxy(Class<T> proxyClass) {
        //TODO
        return null;
    }
}
