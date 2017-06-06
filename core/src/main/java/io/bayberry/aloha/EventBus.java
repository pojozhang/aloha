package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class EventBus extends LifeCycleContext {

    private final SubscriberRegistry subscriberRegistry;
    private final ListenerRegistry listenerRegistry;
    private final SubscriberResolver subscriberResolver;
    private final ChannelResolver channelResolver;
    private Serializer serializer;
    private Deserializer deserializer;

    protected EventBus() {
        this.subscriberRegistry = this.subscriberRegistry();
        this.listenerRegistry = this.listenerRegistry();
        this.subscriberResolver = this.subscriberResolver();
        this.channelResolver = this.channelResolver();
        this.serializer = serializer();
        this.deserializer = deserializer();
    }

    @Override
    protected void onCreate() {
    }

    protected abstract SubscriberRegistry subscriberRegistry();

    protected abstract ListenerRegistry listenerRegistry();

    protected abstract ChannelResolver channelResolver();

    protected abstract SubscriberResolver subscriberResolver();

    protected abstract Serializer serializer();

    protected abstract Deserializer deserializer();

    protected abstract Listener listener(String channel);

    public void post(Object event) {
        this.post(this.getChannelResolver().resolve(event.getClass()), event);
    }

    public abstract void post(String channel, Object event);

    public void register(Object subscriber) {
        this.subscriberRegistry.register(this.subscriberResolver.resolve(subscriber, this));
    }

    public void unregister(Object subscriber) {
        this.subscriberRegistry.unregister(this.subscriberResolver.resolve(subscriber, this));
    }

    @Override
    public void onStart() {
        this.subscriberRegistry.getChannels().forEach(channel -> {
            Listener listener = this.listener(channel);
            this.getListenerRegistry().register(listener);
            listener.start();
        });
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }

    public ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    public Serializer getSerializer() {
        return this.serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public Deserializer getDeserializer() {
        return this.deserializer;
    }

    public void setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
    }

    public SubscriberRegistry getSubscriberRegistry() {
        return subscriberRegistry;
    }

    public SubscriberResolver getSubscriberResolver() {
        return subscriberResolver;
    }

    public ChannelResolver getChannelResolver() {
        return channelResolver;
    }
}
