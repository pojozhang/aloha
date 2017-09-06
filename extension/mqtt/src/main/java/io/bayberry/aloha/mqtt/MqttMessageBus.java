package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.AlohaException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttMessageBus extends RemoteMessageBus implements SubscribableChannel {

    private static final Logger log = LoggerFactory.getLogger(MqttMessageBus.class);
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
            if (this.client != null)
                this.client.disconnect();
        } catch (MqttException e) {

        }
        super.onDestroy();
    }

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return new MqttReceiver(listener.getChannel(), this);
    }

    @Override
    public void publish(Object message) {
        this.publish(this.getChannelResolver().resolve(message.getClass()), message);
    }

    @Override
    public void publish(Channel channel, Object message) {
        this.post(this.mqttCommand, channel, message);
    }

    public MqttMessageBusOptions getOptions() {
        return options;
    }

    @Override
    public <T> T proxy(Class<T> proxyClass) {
        //TODO
        return null;
    }

    private class MqttCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            MqttMessage mqttMessage = new MqttMessage(((String) MqttMessageBus.this.getSerializer().serialize(message)).getBytes());
            mqttMessage.setQos(options.getQos());
            try {
                client.publish(channel.getName(), mqttMessage);
            } catch (MqttException e) {
                throw new AlohaException(e);
            }
        }
    }
}
