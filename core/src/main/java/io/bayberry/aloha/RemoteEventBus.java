package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class RemoteEventBus extends AbstractEventBus {

    protected Serializer serializer;
    protected Deserializer deserializer;

    @Override
    protected void onCreate() {
        this.serializer = this.initSerializer();
        this.deserializer = this.initDeserializer();
        super.onCreate();
    }

    protected abstract Serializer initSerializer();

    protected abstract Deserializer initDeserializer();

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
