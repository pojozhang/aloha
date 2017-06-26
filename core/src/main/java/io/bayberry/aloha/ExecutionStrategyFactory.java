package io.bayberry.aloha;

@FunctionalInterface
public interface ExecutionStrategyFactory {

    ExecutionStrategy provide(Class<? extends ExecutionStrategy> executionStrategyType);
}
