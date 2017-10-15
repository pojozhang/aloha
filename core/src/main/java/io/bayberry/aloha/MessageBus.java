package io.bayberry.aloha;

import io.bayberry.aloha.exception.UnsupportedMessageException;

public interface MessageBus extends LifeCycle {

    void post(Message message) throws UnsupportedMessageException;

    void register(Object container);

    void unregister(Object container);

    ExceptionHandler getDefaultExceptionHandler();

    void setDefaultExceptionHandler(ExceptionHandler exceptionHandler);

    ExceptionHandlerFactory getExceptionHandlerFactory();

    void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory);

    ExecutionStrategy getDefaultExecutionStrategy();

    void setDefaultExecutionStrategy(ExecutionStrategy defaultExecutionStrategy);

    ExecutionStrategyFactory getExecutionStrategyFactory();

    void setExecutionStrategyFactory(ExecutionStrategyFactory executionStrategyFactory);

    Channel resolveOutboundChannel(Class messageType);
}
