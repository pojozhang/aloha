package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.config.AutoConfigurable;
import io.bayberry.aloha.config.PropertySource;

public class MqttAutoConfiguration implements AutoConfigurable<MqttMessageBus> {

    @Override
    public MqttMessageBus create(PropertySource propertySource) {
        return null;
    }
}
