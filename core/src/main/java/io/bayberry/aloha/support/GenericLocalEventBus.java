package io.bayberry.aloha.support;

import io.bayberry.aloha.*;

public abstract class GenericLocalEventBus extends LocalEventBus {

    @Override
    protected SubscriberRegistry initSubscriberRegistry() {
        return new GenericSubscriberRegistry();
    }

    @Override
    protected ListenerRegistry initListenerRegistry() {
        return new GenericListenerRegistry();
    }

    @Override
    protected ChannelResolver initChannelResolver() {
        return new GenericChannelResolver();
    }

    @Override
    protected ExceptionHandler defaultExceptionHandler() {
        return new LogExceptionHandler();
    }

    @Override
    protected ExceptionHandlerProvider exceptionHandlerProvider() {
        return new GenericExceptionHandlerProvider();
    }

    @Override
    protected SubscriberResolver initSubscriberResolver() {
        return new GenericRemoteSubscriberResolver();
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }
}
