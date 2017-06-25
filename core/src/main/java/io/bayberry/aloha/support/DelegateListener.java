package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.Subscriber;

import java.util.List;

public class DelegateListener implements Listener {

    protected Listener delegate;

    public DelegateListener(Listener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void start() {
        this.delegate.start();
    }

    @Override
    public void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public void notifyAll(Object source) {
        this.delegate.notifyAll(source);
    }

    @Override
    public void register(List<Subscriber> subscribers) {
        this.delegate.register(subscribers);
    }

    @Override
    public void register(Subscriber subscriber) {
        this.delegate.register(subscriber);
    }

    @Override
    public void unregister(List<Subscriber> subscribers) {
        this.delegate.unregister(subscribers);
    }

    @Override
    public void unregister(Subscriber subscriber) {
        this.delegate.unregister(subscriber);
    }

    @Override
    public String getChannel() {
        return this.delegate.getChannel();
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return this.delegate.getSubscribers();
    }

    @Override
    public EventBus getEventBus() {
        return this.delegate.getEventBus();
    }

    @Override
    public void handleException(Exception exception, Object value) throws Exception {
        this.delegate.handleException(exception, value);
    }
}
