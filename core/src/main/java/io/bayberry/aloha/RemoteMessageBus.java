package io.bayberry.aloha;

import io.bayberry.aloha.support.*;
import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class RemoteMessageBus extends AbstractMessageBus {

    protected Serializer serializer;
    protected Deserializer deserializer;

    @Override
    protected void onCreate() {
        this.serializer = this.initSerializer();
        this.deserializer = this.initDeserializer();
        super.onCreate();
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public Deserializer getDeserializer() {
        return deserializer;
    }

    public void setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
    }

    protected Serializer initSerializer() {
        return new JsonSerializer();
    }

    protected Deserializer initDeserializer() {
        return new JsonDeserializer();
    }

    @Override
    public ListenerRegistry initListenerRegistry() {
        return new DefaultListenerRegistry();
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
    public ListenerResolver initListenerResolver() {
        return new DefaultListenerResolver();
    }

    @Override
    public void onDestroy() {
        this.getReceiverRegistry().getReceivers().forEach(Receiver::stop);
    }
}
