package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

public abstract class RemoteListener extends Listener {

    public RemoteListener(final String channel, final RemoteEventBus eventBus) {
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

    protected Object getConvertedEventObject(Object value) {
        Deserializer deserializer = getEventBus().getDeserializer();
        return deserializer == null ? value : deserializer.deserialize(value);
    }
}
