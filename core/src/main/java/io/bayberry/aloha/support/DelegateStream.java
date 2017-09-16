package io.bayberry.aloha.support;

import io.bayberry.aloha.*;

import java.util.List;

public class DelegateStream implements Stream {

    protected Stream delegate;

    public DelegateStream(Stream delegate) {
        this.delegate = delegate;
    }

    @Override
    public void start() {
        this.delegate.start();
    }

    @Override
    public void stop() {
        this.delegate.stop();
    }

    @Override
    public void notifyAll(Object source) {
        this.delegate.notifyAll(source);
    }

    @Override
    public void register(List<Listener> listeners) {
        this.delegate.register(listeners);
    }

    @Override
    public void register(Listener listener) {
        this.delegate.register(listener);
    }

    @Override
    public void unregister(List<Listener> listeners) {
        this.delegate.unregister(listeners);
    }

    @Override
    public void unregister(Listener listener) {
        this.delegate.unregister(listener);
    }

    @Override
    public Channel getChannel() {
        return this.delegate.getChannel();
    }

    @Override
    public List<Listener> getListeners() {
        return this.delegate.getListeners();
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
