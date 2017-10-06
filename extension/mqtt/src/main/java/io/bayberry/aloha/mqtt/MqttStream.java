package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.RemoteStream;
import io.bayberry.aloha.exception.AlohaException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttStream extends RemoteStream implements MqttCallback {

    private MqttMessageBusOptions options;
    private MqttClient client;

    public MqttStream(Channel channel, MqttMessageBus messageBus) {
        super(channel, messageBus);
        this.options = messageBus.getOptions();
    }

    @Override
    protected void onStart() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            this.client = new MqttClient(options.getServerUri(), options.getClientId(), persistence);
            this.client.setCallback(this);
            this.client.connect(((MqttMessageBus) getMessageBus()).getOptions().getConnectOptions());
            this.client.subscribe(getChannel().getName());
        } catch (MqttException e) {
            throw new AlohaException(e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        super.notifyAll(message.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    protected void onStop() {
        try {
            this.client.disconnect();
        } catch (MqttException e) {
        }
        super.onStop();
    }
}
