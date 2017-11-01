package io.bayberry.aloha.config;

public abstract class PropertySource {

    public abstract <T> T get(String property);

    public <T> T get(String property, T defaultValue) {
        if (!contains(property)) return defaultValue;
        return get(property);
    }

    public abstract boolean contains(String property);
}
