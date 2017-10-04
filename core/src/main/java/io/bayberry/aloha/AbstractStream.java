package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlohaException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStream extends MessageBusContext implements Stream {

    protected Channel channel;
    protected List<Listener> listeners = new ArrayList<>();

    public AbstractStream(Channel channel, MessageBus messageBus) {
        super(messageBus);
        this.channel = channel;
    }

    @Override
    public void notifyAll(Object source) {
        this.listeners.forEach(listener -> {
            try {
                listener.accept(this, this.getConvertedMessage(source, listener));
            } catch (Exception exception) {
                try {
                    handleException(exception, source);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected abstract Object getConvertedMessage(Object origin, Listener listener);

    @Override
    public void handleException(Exception exception, Object value) throws Exception {
        this.getMessageBus().getDefaultExceptionHandler().handle(getChannel(), value, getMessageBus(), exception);
    }

    @Override
    protected void onCreate() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void register(List<Listener> listeners) {
        listeners.forEach(this::register);
    }

    @Override
    public void register(Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unregister(List<Listener> listeners) {
        listeners.forEach(this::unregister);
    }

    @Override
    public void unregister(Listener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public List<Listener> getListeners() {
        return listeners;
    }
}
