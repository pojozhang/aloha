package io.bayberry.aloha;

import com.google.common.collect.Lists;
import io.bayberry.aloha.exception.AlohaException;

import java.util.List;

public abstract class Listener extends LifeCycleContext {

    protected final List<Subscriber> subscribers = Lists.newArrayList();
    private final String channel;
    private final EventBus eventBus;

    public Listener(final String channel, final EventBus eventBus) {
        this.channel = channel;
        this.eventBus = eventBus;
    }

    public void notifyAll(Object value) {
        this.subscribers.forEach(subscriber -> {
            try {
                subscriber.invoke(value);
            } catch (Exception e) {
                throw new AlohaException(e);
            }
        });
    }

    @Override
    protected void onCreate() {
    }

    @Override
    protected void onDestroy() {
    }

    public void register(List<Subscriber> subscribers) {
        subscribers.forEach(this::register);
    }

    public void register(Subscriber subscriber) {
        this.subscribers.add(subscriber);
        subscriber.setListener(this);
    }

    public void unregister(List<Subscriber> subscribers) {
        subscribers.forEach(this::unregister);
    }

    public void unregister(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public final String getChannel() {
        return channel;
    }

    public final EventBus getEventBus() {
        return eventBus;
    }
}
