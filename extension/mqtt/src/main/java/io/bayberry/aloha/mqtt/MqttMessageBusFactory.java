package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.MessageBusConfig;
import io.bayberry.aloha.config.ConfigurableFactory;

public class MqttMessageBusFactory implements ConfigurableFactory<MqttMessageBus, MqttMessageBusOptions> {

    @Override
    public MqttMessageBus create(MqttMessageBusOptions options, MessageBusConfig config) {
        return null;
    }
}
