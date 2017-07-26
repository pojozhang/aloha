package io.bayberry.aloha;

public interface MessageBus extends LifeCycle {

    SubscriberRegistry initSubscriberRegistry();

    ReceiverRegistry initReceiverRegistry();

    SubscriberResolver initSubscriberResolver();

    ChannelResolver initChannelResolver();

    Receiver bindListener(Channel channel);

    ExceptionHandler initDefaultExceptionHandler();

    ExceptionHandlerFactory initExceptionHandlerFactory();

    ExecutionStrategy initDefaultExecutionStrategy();

    ExecutionStrategyFactory initExecutionStrategyFactory();

    void produce(Object message);

    void produce(Channel channel, Object message);

    void publish(Object message);

    void publish(Channel channel, Object message);

    void register(Object target);

    void unregister(Object target);

    ReceiverRegistry getReceiverRegistry();

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
