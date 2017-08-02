package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.*;

public class MqttMessageBus extends RemoteMessageBus implements Publisher {

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return null;
    }

    @Override
    public void publish(Object message) {

    }

    @Override
    public void publish(Channel channel, Object message) {

    }
}
