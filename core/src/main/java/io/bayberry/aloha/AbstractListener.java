package io.bayberry.aloha;

import com.google.common.collect.Lists;
import io.bayberry.aloha.exception.AlohaException;

import java.util.List;

public abstract class AbstractListener extends EventBusContext implements Listener {

    protected Channel channel;
    protected List<Subscriber> subscribers = Lists.newArrayList();

    public AbstractListener(Channel channel, EventBus eventBus) {
        super(eventBus);
        this.channel = channel;
    }

    @Override
    public void notifyAll(Object source) {
        this.subscribers.forEach(subscriber -> {
            try {
                subscriber.accept(this, this.getConvertedEventObject(source, subscriber));
            } catch (Exception exception) {
                try {
                    handleException(exception, source);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected abstract Object getConvertedEventObject(Object origin, Subscriber subscriber);

    @Override
    public void handleException(Exception exception, Object value) throws Exception {
        this.getEventBus().getDefaultExceptionHandler().handle(getChannel(), value, getEventBus(), exception);
    }

    @Override
    protected void onCreate() {
    }

    @Override
    public void register(List<Subscriber> subscribers) {
        subscribers.forEach(this::register);
    }

    @Override
    public void register(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void unregister(List<Subscriber> subscribers) {
        subscribers.forEach(this::unregister);
    }

    @Override
    public void unregister(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return subscribers;
    }
}
