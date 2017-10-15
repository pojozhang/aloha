package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.*;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.UncheckedAlohaException;
import io.bayberry.aloha.exception.UnsupportedListenerException;
import io.bayberry.aloha.exception.UnsupportedMessageException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Collections;

public class MqttMessageBus extends RemoteMessageBus<Object, byte[]> {

    private MqttMessageBusOptions options;
    private MqttClient client;
    private MqttCommand mqttCommand;

    public MqttMessageBus(MqttMessageBusOptions options) {
        this.options = options;
        this.mqttCommand = new MqttCommand();
        this.onCreate();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            this.client = new MqttClient(options.getServerUri(), options.getClientId(), persistence);
        } catch (MqttException e) {
            throw new UncheckedAlohaException(e);
        }
    }

    @Override
    public void onStart() {
        try {
            this.client.connect(this.options.getConnectOptions());
        } catch (MqttException e) {
            throw new UncheckedAlohaException(e);
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        try {
            if (this.client != null) {
                this.client.disconnect();
            }
        } catch (MqttException e) {

        }
        super.onStop();
    }

    @Override
    protected ListenerResolver initListenerResolver() {
        return new AnnotatedListenerResolver(Collections.singletonMap(Subscribe.class, new SubscriberAnnotationResolver()));
    }

    @Override
    protected Stream bindStream(Channel channel, Listener listener) throws UnsupportedListenerException {
        if (listener instanceof Subscriber) {
            return new MqttStream(channel, this);
        }
        throw new UnsupportedListenerException(listener);
    }

    public MqttMessageBusOptions getOptions() {
        return options;
    }

    @Override
    public void post(Message message) throws UnsupportedMessageException {
        if (message instanceof SubscribableMessage) {
            this.mqttCommand.execute(message.getChannel(), message.getPayload());
            return;
        }
        throw new UnsupportedMessageException(message);
    }

    private class MqttCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            if (channel == null) {
                channel = MqttMessageBus.this.getChannelResolver().resolve(message.getClass());
            }
            MqttMessage mqttMessage = new MqttMessage(MqttMessageBus.this.getSerializer().serialize(message));
            mqttMessage.setQos(options.getQos());
            try {
                client.publish(channel.getName(), mqttMessage);
            } catch (MqttException e) {
                throw new UncheckedAlohaException(e);
            }
        }
    }
}
