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
    private ExceptionHandlerProvider exceptionHandlerProvider;

    protected abstract SubscriberRegistry subscriberRegistry();

    protected abstract ListenerRegistry listenerRegistry();

    protected abstract ChannelResolver channelResolver();

    protected abstract SubscriberResolver subscriberResolver();

    protected abstract Serializer serializer();

    protected abstract Deserializer deserializer();

    protected abstract Listener bindListener(String channel);

    protected abstract ExceptionHandler defaultExceptionHandler();

    protected abstract ExceptionHandlerProvider exceptionHandlerProvider();

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
        this.exceptionHandlerProvider = exceptionHandlerProvider();
    }

    @Override
    public void onStart() {
        this.subscriberRegistry.getChannels().forEach(channel -> {
            Listener listener = this.bindListener(channel);
            listener.register(this.subscriberRegistry.getSubscribers(channel));
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

    public ExceptionHandler getDefaultExceptionHandler() {
        return defaultExceptionHandler;
    }

    public void setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {
        this.defaultExceptionHandler = exceptionHandler;
    }

    public ExceptionHandlerProvider getExceptionHandlerProvider() {
        return exceptionHandlerProvider;
    }

    public void setExceptionHandlerProvider(ExceptionHandlerProvider exceptionHandlerProvider) {
        this.exceptionHandlerProvider = exceptionHandlerProvider;
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
