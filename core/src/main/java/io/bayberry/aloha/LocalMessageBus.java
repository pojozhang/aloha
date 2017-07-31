package io.bayberry.aloha;

import io.bayberry.aloha.support.GenericExecutionStrategy;
import io.bayberry.aloha.support.GenericExecutionStrategyFactory;
import io.bayberry.aloha.support.DefaultLogExceptionHandler;

public abstract class LocalMessageBus extends AbstractMessageBus {

    @Override
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new GenericExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new GenericExecutionStrategyFactory();
    }
}
