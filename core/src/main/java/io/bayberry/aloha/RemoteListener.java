package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

public abstract class RemoteListener extends AbstractListener {

    public RemoteListener(String channel, RemoteEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    public RemoteEventBus getEventBus() {
        return (RemoteEventBus) super.getEventBus();
    }

    @Override
    public void notifyAll(Object value) {
        super.notifyAll(value);
    }

    @Override
    protected Object getConvertedEventObject(Object origin, Subscriber subscriber) {
        Deserializer deserializer = getEventBus().getDeserializer();
        return deserializer == null ? origin : deserializer.deserialize(origin, subscriber.getMethod().getParameterTypes()[0]);
    }
}
