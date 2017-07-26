package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class GenericRemoteMessageBus extends RemoteMessageBus {

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
        return new DefaultChannelResolver();
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
        return new DefaultExceptionHandlerFactory();
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
