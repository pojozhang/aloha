package io.bayberry.aloha;

public interface MessageBus extends LifeCycle {

    SubscriberRegistry initSubscriberRegistry();

    ListenerRegistry initListenerRegistry();

    SubscriberResolver initSubscriberResolver();

    ChannelResolver initChannelResolver();

    Listener bindListener(Channel channel);

    ExceptionHandler initDefaultExceptionHandler();

    ExceptionHandlerFactory initExceptionHandlerFactory();

    ExecutionStrategy initDefaultExecutionStrategy();

    ExecutionStrategyFactory initExecutionStrategyFactory();

    void post(Object message);

    void post(Channel channel, Object message);

    void register(Object target);

    void unregister(Object target);

    ListenerRegistry getListenerRegistry();

    ExceptionHandler getDefaultExceptionHandler();

    void setDefaultExceptionHandler(ExceptionHandler exceptionHandler);

    ExceptionHandlerFactory getExceptionHandlerFactory();

    void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory);

    ExecutionStrategy getDefaultExecutionStrategy();

    void setDefaultExecutionStrategy(ExecutionStrategy defaultExecutionStrategy);

    ExecutionStrategyFactory getExecutionStrategyFactory();

    void setExecutionStrategyFactory(ExecutionStrategyFactory executionStrategyFactory);

    SubscriberRegistry getSubscriberRegistry();

    SubscriberResolver getSubscriberResolver();

    ChannelResolver getChannelResolver();
}
