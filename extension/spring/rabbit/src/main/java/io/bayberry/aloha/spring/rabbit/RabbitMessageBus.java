package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.*;

public class RabbitMessageBus extends RemoteMessageBus {

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return null;
    }

    @Override
    public void post(Message message) {

    }
}
