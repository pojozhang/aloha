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
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new GenericExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new GenericExecutionStrategyFactory();
    }
}
