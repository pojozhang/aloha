package io.bayberry.aloha.config;

import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.MessageBusConfig;

public interface ConfigurableFactory<T extends MessageBus, O> {

    T create(O options, MessageBusConfig config);
}
