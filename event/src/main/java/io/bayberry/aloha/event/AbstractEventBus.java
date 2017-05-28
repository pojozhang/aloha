package io.bayberry.aloha.event;

import com.google.common.collect.Sets;

import java.util.Set;

public abstract class AbstractEventBus implements EventBus {

    protected final Set<EventSubscriber> listeners;

    public AbstractEventBus() {
        this.listeners = Sets.newConcurrentHashSet();
    }

    public void register(EventSubscriber listener) {
        this.listeners.add(listener);
    }

    public void unregister(EventSubscriber listener) {
        this.listeners.remove(listener);
    }
}
