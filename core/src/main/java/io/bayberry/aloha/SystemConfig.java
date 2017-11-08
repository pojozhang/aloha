package io.bayberry.aloha;

import java.util.HashMap;
import java.util.Map;

class SystemConfig {

    private Map<String, MessageBusConfig> configs = new HashMap<>();

    Map<String, MessageBusConfig> getMessageBusConfigs() {
        return configs;
    }

    MessageBusConfig getMessageBusConfig(String name) {
        return this.configs.get(name);
    }

    void add(MessageBusConfig messageBusConfig) {
        this.configs.put(messageBusConfig.getName(), messageBusConfig);
    }
}
