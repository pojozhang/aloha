package io.bayberry.aloha;

import io.bayberry.aloha.support.DefaultReceiverRegistry;
import io.bayberry.aloha.support.GenericChannelResolver;
import io.bayberry.aloha.support.GenericExceptionHandlerFactory;
import io.bayberry.aloha.support.GenericExecutionStrategy;
import io.bayberry.aloha.support.GenericExecutionStrategyFactory;
import io.bayberry.aloha.support.GenericSubscriberRegistry;
import io.bayberry.aloha.support.GenericSubscriberResolver;
import io.bayberry.aloha.support.LogExceptionHandler;

public abstract class LocalMessageBus extends AbstractMessageBus {

    @Override
    public SubscriberRegistry initSubscriberRegistry() {
        return new GenericSubscriberRegistry();
    }

    @Override
    public ReceiverRegistry initReceiverRegistry() {
        return new DefaultReceiverRegistry();
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new GenericChannelResolver();
    }

    @Override
    public ExceptionHandler initDefaultExceptionHandler() {
        return new LogExceptionHandler();
    }

    @Override
    public ExceptionHandlerFactory initExceptionHandlerFactory() {
        return new GenericExceptionHandlerFactory();
    }

    @Override
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new GenericExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new GenericExecutionStrategyFactory();
    }

    @Override
    public SubscriberResolver initSubscriberResolver() {
        return new GenericSubscriberResolver();
    }

    @Override
    public void onDestroy() {
        this.getReceiverRegistry().getReceivers().forEach(Receiver::shutdown);
    }
}
