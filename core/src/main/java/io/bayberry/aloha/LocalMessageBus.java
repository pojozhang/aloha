package io.bayberry.aloha;

import io.bayberry.aloha.support.DefaultExecutionStrategy;
import io.bayberry.aloha.support.DefaultExecutionStrategyFactory;

public abstract class LocalMessageBus extends AbstractMessageBus {

    protected LocalMessageBus(String name, PropertySource propertySource) {
        super(name, propertySource);
    }

    @Override
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new DefaultExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new DefaultExecutionStrategyFactory();
    }
}
