package io.bayberry.aloha;

public abstract class LocalListener extends AbstractListener {

    public LocalListener(Channel channel, LocalMessageBus messageBus) {
        super(channel, messageBus);
    }

    @Override
    public LocalMessageBus getMessageBus() {
        return (LocalMessageBus) super.getMessageBus();
    }

    @Override
    protected Object getConvertedMessage(Object origin, Subscriber subscriber) {
        return origin;
    }
}
