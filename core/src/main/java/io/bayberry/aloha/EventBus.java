package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;
import io.bayberry.aloha.transport.Serializer;

public abstract class EventBus extends LifeCycleContext {

    private SubscriberRegistry subscriberRegistry;
    private ListenerRegistry listenerRegistry;
    private SubscriberResolver subscriberResolver;
    private ChannelResolver channelResolver;
    private Serializer serializer;
    private Deserializer deserializer;
    private ExceptionHandler defaultExceptionHandler;

    protected abstract SubscriberRegistry subscriberRegistry();

    protected abstract ListenerRegistry listenerRegistry();

    protected abstract ChannelResolver channelResolver();

    protected abstract SubscriberResolver subscriberResolver();

    protected abstract Serializer serializer();

    protected abstract Deserializer deserializer();

    protected abstract Listener listener(String channel);

    protected abstract ExceptionHandler defaultExceptionHandler();

    public void post(Object event) {
        this.post(this.getChannelResolver().resolve(event.getClass()), event);
    }

    public void post(String channel, Object event) {
        this.post(channel, (String) getSerializer().serialize(event));
    }

    public abstract void post(String channel, String message);

    public void register(Object subscriber) {
        this.subscriberRegistry.register(this.subscriberResolver.resolve(subscriber, this));
    }

    public void unregister(Object subscriber) {
        this.subscriberRegistry.unregister(this.subscriberResolver.resolve(subscriber, this));
    }

    @Override
    protected void onCreate() {
        this.subscriberRegistry = this.subscriberRegistry();
        this.listenerRegistry = this.listenerRegistry();
        this.subscriberResolver = this.subscriberResolver();
        this.channelResolver = this.channelResolver();
        this.serializer = this.serializer();
        this.deserializer = this.deserializer();
        this.defaultExceptionHandler = this.defaultExceptionHandler();
    }

    @Override
    public void onStart() {
        this.subscriberRegistry.getChannels().forEach(channel -> {
            Listener listener = this.listener(channel);
            listener.register(this.subscriberRegistry.getSubscribers(channel));
            this.getListenerRegistry().register(listener);
            listener.start();
        });
    }

    @Override
    public void onDestroy() {
        this.getListenerRegistry().getListeners().forEach(Listener::shutdown);
    }

    public final ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    public final Serializer getSerializer() {
        return this.serializer;
    }

    public final void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public final Deserializer getDeserializer() {
        return this.deserializer;
    }

    public final void setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
    }

    public final ExceptionHandler getDefaultExceptionHandler() {
        return defaultExceptionHandler;
    }

    public final void setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {
        this.defaultExceptionHandler = exceptionHandler;
    }

    public final SubscriberRegistry getSubscriberRegistry() {
        return subscriberRegistry;
    }

    public final SubscriberResolver getSubscriberResolver() {
        return subscriberResolver;
    }

    public final ChannelResolver getChannelResolver() {
        return channelResolver;
    }
}
