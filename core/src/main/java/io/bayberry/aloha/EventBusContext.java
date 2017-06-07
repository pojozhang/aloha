package io.bayberry.aloha;

public abstract class EventBusContext extends LifeCycleContext {

    private final EventBus eventBus;

    public EventBusContext(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
