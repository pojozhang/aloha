package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.Subscriber;

import java.util.List;

public class DelegateListener implements Receiver {

    protected Receiver delegate;

    public DelegateListener(Receiver delegate) {
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
    public Channel getChannel() {
        return this.delegate.getChannel();
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return this.delegate.getSubscribers();
    }

    @Override
    public MessageBus getMessageBus() {
        return this.delegate.getMessageBus();
    }

    @Override
    public void handleException(Exception exception, Object value) throws Exception {
        this.delegate.handleException(exception, value);
    }
}
