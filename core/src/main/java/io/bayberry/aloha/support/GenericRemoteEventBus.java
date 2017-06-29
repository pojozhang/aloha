package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.JsonDeserializer;
import io.bayberry.aloha.transport.JsonSerializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class GenericRemoteEventBus extends RemoteEventBus {

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
        return new SimpleChannelResolver();
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
