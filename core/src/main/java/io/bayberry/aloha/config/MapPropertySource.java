package io.bayberry.aloha.config;

import java.util.Map;

public class MapPropertySource extends PropertySource {

    private Map<String, Object> source;

    public MapPropertySource(Map<String, Object> source) {
        this.source = source;
    }

    @Override
    public <T> T get(String property) {
        return (T) this.source.get(property);
    }

    @Override
    public boolean contains(String property) {
        return this.source.containsKey(property);
    }
}
