package io.bayberry.aloha;

import io.bayberry.aloha.support.*;
import io.bayberry.aloha.support.serializer.JsonSerializer;
import io.bayberry.aloha.support.serializer.Serializer;

public abstract class RemoteMessageBus<S, T> extends AbstractMessageBus {

    protected Serializer<S, T> serializer;

    protected RemoteMessageBus(String name, PropertySource propertySource) {
        super(name, propertySource);
    }

    @Override
    protected void onCreate() {
        this.serializer = this.initSerializer();
        super.onCreate();
    }

    public Serializer<S, T> getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer<S, T> serializer) {
        this.serializer = serializer;
    }

    protected Serializer<S, T> initSerializer() {
        return new JsonSerializer();
    }

    @Override
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new DefaultExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new DefaultExecutionStrategyFactory();
    }
}
