package io.bayberry.aloha;

public abstract class LocalReceiver extends AbstractReceiver {

    public LocalReceiver(Channel channel, LocalMessageBus messageBus) {
        super(channel, messageBus);
    }

    @Override
    public LocalMessageBus getMessageBus() {
        return (LocalMessageBus) super.getMessageBus();
    }

    @Override
    protected Object getConvertedMessage(Object origin, Listener listener) {
        return origin;
    }
}
