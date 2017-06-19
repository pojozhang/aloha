package io.bayberry.aloha;

public abstract class RemoteListener extends Listener {

    public RemoteListener(final String channel, final RemoteEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    public RemoteEventBus getEventBus() {
        return (RemoteEventBus) super.getEventBus();
    }
}
