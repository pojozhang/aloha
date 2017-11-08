package io.bayberry.aloha;

import java.util.Map;

class MessageBusConfig {

    private String name;
    private String type;
    private Map<String, Object> properties;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    Map<String, Object> getProperties() {
        return properties;
    }

    void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
