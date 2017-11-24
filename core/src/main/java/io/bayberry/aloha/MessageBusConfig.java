package io.bayberry.aloha;

import java.util.Map;

public class MessageBusConfig {

    private String name;
    private String type;
    private Map<String, Object> properties;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
