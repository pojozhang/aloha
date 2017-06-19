package io.bayberry.aloha;

public abstract class LocalListener extends Listener {

    public LocalListener(final String channel, final LocalEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    public LocalEventBus getEventBus() {
        return (LocalEventBus) super.getEventBus();
    }
}
