package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.ExecutionStrategyFactory;
import io.bayberry.aloha.exception.UncheckedAlohaException;

public class DefaultExecutionStrategyFactory implements ExecutionStrategyFactory {

    @Override
    public ExecutionStrategy provide(Class<? extends ExecutionStrategy> executionStrategyType) {
        try {
            return executionStrategyType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UncheckedAlohaException(e);
        }
    }
}
