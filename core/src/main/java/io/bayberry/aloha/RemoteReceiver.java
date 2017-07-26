package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

public abstract class RemoteReceiver extends AbstractReceiver {

    public RemoteReceiver(Channel channel, RemoteMessageBus messageBus) {
        super(channel, messageBus);
    }

    @Override
    public RemoteMessageBus getMessageBus() {
        return (RemoteMessageBus) super.getMessageBus();
    }

    @Override
    public void notifyAll(Object source) {
        super.notifyAll(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getConvertedMessage(Object origin, Subscriber subscriber) {
        Deserializer deserializer = getMessageBus().getDeserializer();
        return deserializer == null ? origin
                : deserializer.deserialize(origin, subscriber.getMethod().getParameterTypes()[0]);
    }
}
