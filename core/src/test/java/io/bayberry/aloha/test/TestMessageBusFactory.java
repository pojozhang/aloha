package io.bayberry.aloha.test;

import io.bayberry.aloha.MessageBusConfig;
import io.bayberry.aloha.config.ConfigurableFactory;

public class TestMessageBusFactory implements ConfigurableFactory<TestMessageBus, TestMessageBusOptions> {

    @Override
    public TestMessageBus create(TestMessageBusOptions options, MessageBusConfig config) {
        return null;
    }
}
