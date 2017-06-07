package io.bayberry.aloha;

import com.google.common.collect.Lists;
import io.bayberry.aloha.exception.AlohaException;

import java.util.List;

public abstract class Listener extends EventBusContext {

    protected final List<Subscriber> subscribers = Lists.newArrayList();
    private final String channel;

    public Listener(final String channel, final EventBus eventBus) {
        super(eventBus);
        this.channel = channel;
    }

    public void notifyAll(Object value) {
        this.subscribers.forEach(subscriber -> {
            try {
                subscriber.accept(value);
            } catch (Exception exception) {
                try {
                    handleException(exception, value);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected void handleException(Exception exception, Object value) throws Exception {
        this.getEventBus().getDefaultExceptionHandler().handle(getChannel(), value, getEventBus(), exception);
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

    public String getChannel() {
        return channel;
    }
}
