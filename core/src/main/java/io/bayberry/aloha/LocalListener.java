package io.bayberry.aloha;

public abstract class LocalListener extends AbstractListener {

    public LocalListener(Channel channel, LocalEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    public LocalEventBus getEventBus() {
        return (LocalEventBus) super.getEventBus();
    }

    @Override
    protected Object getConvertedEventObject(Object origin, Subscriber subscriber) {
        return origin;
    }
}
