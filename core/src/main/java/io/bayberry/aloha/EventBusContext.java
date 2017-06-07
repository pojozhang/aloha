package io.bayberry.aloha;

public abstract class EventBusContext extends LifeCycleContext {

    protected final EventBus eventBus;

    public EventBusContext(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    protected final EventBus getEventBus() {
        return eventBus;
    }
}
