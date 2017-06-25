package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.JsonDeserializer;
import io.bayberry.aloha.transport.JsonSerializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class GenericRemoteEventBus extends RemoteEventBus {

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
    protected Serializer initSerializer() {
        return new JsonSerializer();
    }

    @Override
    protected Deserializer initDeserializer() {
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
    protected SubscriberResolver initSubscriberResolver() {
        return new GenericSubscriberResolver();
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }
}
