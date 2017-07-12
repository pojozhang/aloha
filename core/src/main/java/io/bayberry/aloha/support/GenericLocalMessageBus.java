package io.bayberry.aloha.support;

import io.bayberry.aloha.*;

public abstract class GenericLocalMessageBus extends LocalMessageBus {

    @Override
    public SubscriberRegistry initSubscriberRegistry() {
        return new GenericSubscriberRegistry();
    }

    @Override
    public ListenerRegistry initListenerRegistry() {
        return new GenericListenerRegistry();
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
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }
}
