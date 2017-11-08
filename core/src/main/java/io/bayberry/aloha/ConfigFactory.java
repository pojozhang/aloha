package io.bayberry.aloha;

import io.bayberry.aloha.config.PropertySource;
import io.bayberry.aloha.config.YamlPropertySourceReader;

import java.io.InputStream;
import java.util.Map;

public class ConfigFactory {

    public static Config load(InputStream inputStream) {
        Config config = new Config();
        PropertySource propertySource = new YamlPropertySourceReader().read(inputStream);
        Map<String, Map<String, Object>> buses = propertySource.get("aloha.buses");
        buses.forEach((bus, properties) -> {
            MessageBusConfig messageBusConfig = new MessageBusConfig();
            messageBusConfig.setName(bus);
            messageBusConfig.setType((String) properties.get("type"));
            messageBusConfig.setProperties(properties);
            config.add(messageBusConfig);
        });
        return config;
    }
}
