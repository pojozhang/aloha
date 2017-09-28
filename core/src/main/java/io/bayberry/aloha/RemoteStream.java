package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

public abstract class RemoteStream extends AbstractStream {

    public RemoteStream(Channel channel, RemoteMessageBus messageBus) {
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
    protected Object getConvertedMessage(Object origin, Listener listener) {
        Deserializer deserializer = getMessageBus().getDeserializer();
        return deserializer == null ? origin
                : deserializer.deserialize(origin, listener.getMethod().getParameterTypes()[0]);
    }
}
