package io.bayberry.aloha.config;

import io.bayberry.aloha.MessageBus;

public interface AutoConfigurable<T extends MessageBus> {

    T create(PropertySource propertySource);
}
