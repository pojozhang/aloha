package io.bayberry.aloha;

public interface MessageBus<M extends Message> extends LifeCycle {

    void post(M message);

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

    ListenerRegistry getListenerRegistry();

    ListenerResolver getListenerResolver();

    ChannelResolver getChannelResolver();

    ReceiverRegistry getReceiverRegistry();
}
