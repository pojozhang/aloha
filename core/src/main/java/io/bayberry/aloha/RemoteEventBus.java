package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class RemoteEventBus extends EventBus {

    private Serializer serializer;
    private Deserializer deserializer;

    @Override
    protected void onCreate() {
        super.onCreate();
        this.serializer = this.serializer();
        this.deserializer = this.deserializer();
    }

    protected abstract Serializer serializer();

    protected abstract Deserializer deserializer();

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
}
