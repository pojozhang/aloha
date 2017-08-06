package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.RemoteReceiver;
import io.bayberry.aloha.exception.AlohaException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttReceiver extends RemoteReceiver implements MqttCallback {

    private MqttMessageBusOptions options;
    private MqttClient client;

    public MqttReceiver(Channel channel, MqttMessageBus messageBus) {
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
        super.notifyAll(new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    protected void onDestroy() {
        try {
            this.client.disconnect();
        } catch (MqttException e) {
        }
        super.onDestroy();
    }
}
