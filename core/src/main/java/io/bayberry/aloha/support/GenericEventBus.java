package io.bayberry.aloha.support;

import io.bayberry.aloha.*;

public abstract class GenericEventBus extends EventBus {

    @Override
    protected SubscriberRegistry subscriberRegistry() {
        return new GenericSubscriberRegistry();
    }

    @Override
    protected ListenerRegistry listenerRegistry() {
        return new GenericListenerRegistry();
    }

    @Override
    protected ChannelResolver channelResolver() {
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
    protected SubscriberResolver subscriberResolver() {
        return new GenericRemoteSubscriberResolver();
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }
}
