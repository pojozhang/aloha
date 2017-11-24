package io.bayberry.aloha.config;

import io.bayberry.aloha.MessageBus;

public interface ConfigurableFactory<T extends MessageBus> {

    T create(PropertySource propertySource);
}
