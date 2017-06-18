package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.JsonDeserializer;
import io.bayberry.aloha.transport.JsonSerializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class GenericRemoteEventBus extends RemoteEventBus {

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
    protected Serializer serializer() {
        return new JsonSerializer();
    }

    @Override
    protected Deserializer deserializer() {
        return new JsonDeserializer();
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
        return new GenericSubscriberResolver();
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }
}
