package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

public abstract class RemoteListener extends AbstractListener {

    public RemoteListener(Channel channel, RemoteEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    public RemoteEventBus getEventBus() {
        return (RemoteEventBus) super.getEventBus();
    }

    @Override
    public void notifyAll(Object source) {
        super.notifyAll(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getConvertedEventObject(Object origin, Subscriber subscriber) {
        Deserializer deserializer = getEventBus().getDeserializer();
        return deserializer == null ? origin
                : deserializer.deserialize(origin, subscriber.getMethod().getParameterTypes()[0]);
    }
}
