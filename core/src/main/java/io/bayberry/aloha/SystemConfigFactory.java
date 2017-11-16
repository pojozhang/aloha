package io.bayberry.aloha;

import io.bayberry.aloha.config.PropertySource;
import io.bayberry.aloha.config.YamlPropertySourceReader;

import java.io.InputStream;
import java.util.Map;

public class SystemConfigFactory {

    public static SystemConfig load(String resource) {
        return load(SystemConfigFactory.class.getClassLoader().getResourceAsStream(resource));
    }

    public static SystemConfig load(InputStream inputStream) {
        SystemConfig systemConfig = new SystemConfig();
        PropertySource propertySource = new YamlPropertySourceReader().read(inputStream);
        Map<String, Map<String, Object>> buses = propertySource.get("aloha.buses");
        buses.forEach((bus, properties) -> {
            MessageBusConfig messageBusConfig = new MessageBusConfig();
            messageBusConfig.setName(bus);
            messageBusConfig.setType((String) properties.get("type"));
            messageBusConfig.setProperties(properties);
            systemConfig.add(messageBusConfig);
        });
        return systemConfig;
    }
}
