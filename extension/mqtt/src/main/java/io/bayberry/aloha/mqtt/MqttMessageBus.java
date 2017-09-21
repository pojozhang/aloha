package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.AlohaException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttMessageBus extends RemoteMessageBus {

    private MqttMessageBusOptions options;
    private MqttClient client;
    private MqttCommand mqttCommand;

    public MqttMessageBus(MqttMessageBusOptions options) {
        this.options = options;
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.mqttCommand = new MqttCommand();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            this.client = new MqttClient(options.getServerUri(), options.getClientId(), persistence);
            this.client.connect(this.options.getConnectOptions());
        } catch (MqttException e) {
            throw new AlohaException(e);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        try {
            if (this.client != null) {
                this.client.disconnect();
            }
        } catch (MqttException e) {

        }
        super.onDestroy();
    }

    @Override
    protected Stream bindStream(Listener listener) {
        return new MqttStream(listener.getChannel(), this);
    }

    public MqttMessageBusOptions getOptions() {
        return options;
    }

    @Override
    public void post(Message message) {
        if (message instanceof SubscribableMessage) {
            this.mqttCommand.execute(message.getChannel(), message.getPayload());
        }
        super.handleUnsupportedMessage(message);
    }

    private class MqttCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = MqttMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            MqttMessage mqttMessage = new MqttMessage(
                    ((String) MqttMessageBus.this.getSerializer().serialize(message)).getBytes());
            mqttMessage.setQos(options.getQos());
            try {
                client.publish(channel.getName(), mqttMessage);
            } catch (MqttException e) {
                throw new AlohaException(e);
            }
        }
    }
}
