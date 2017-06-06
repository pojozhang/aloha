package io.bayberry.aloha;

import com.google.common.collect.Lists;
import io.bayberry.aloha.exception.AlohaException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Listener extends LifeCycleContext {

    protected final List<Subscriber> subscribers = Lists.newArrayList();
    private final ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private final String channel;

    public Listener(final String channel) {
        this.channel = channel;
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
    public void start() {
        super.start();
        this.threadPool.execute(this::onStart);
    }

    @Override
    public void shutdown() {
        if (!this.threadPool.isShutdown()) {
            this.threadPool.shutdown();
        }
        super.shutdown();
    }

    public void register(List<Subscriber> subscribers) {
        subscribers.forEach(this::register);
    }

    public void register(Subscriber subscriber) {
        this.subscribers.add(subscriber);
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
