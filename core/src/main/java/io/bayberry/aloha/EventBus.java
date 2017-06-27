package io.bayberry.aloha;

public abstract class EventBus extends LifeCycleContext {

    private SubscriberRegistry subscriberRegistry;
    private ListenerRegistry listenerRegistry;
    private SubscriberResolver subscriberResolver;
    private ChannelResolver channelResolver;
    private ExceptionHandler defaultExceptionHandler;
    private ExceptionHandlerFactory exceptionHandlerFactory;
    private ExecutionStrategy defaultExecutionStrategy;
    private ExecutionStrategyFactory executionStrategyFactory;

    protected abstract SubscriberRegistry initSubscriberRegistry();

    protected abstract ListenerRegistry initListenerRegistry();

    protected abstract SubscriberResolver initSubscriberResolver();

    protected abstract ChannelResolver initChannelResolver();

    protected abstract Listener bindListener(String channel);

    protected abstract ExceptionHandler initDefaultExceptionHandler();

    protected abstract ExceptionHandlerFactory initExceptionHandlerFactory();

    protected abstract ExecutionStrategy initDefaultExecutionStrategy();

    protected abstract ExecutionStrategyFactory initExecutionStrategyFactory();

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
    protected void onCreate() {
        this.subscriberRegistry = this.initSubscriberRegistry();
        this.listenerRegistry = this.initListenerRegistry();
        this.subscriberResolver = this.initSubscriberResolver();
        this.channelResolver = this.initChannelResolver();
        this.defaultExceptionHandler = this.initDefaultExceptionHandler();
        this.exceptionHandlerFactory = this.initExceptionHandlerFactory();
        this.defaultExecutionStrategy = this.initDefaultExecutionStrategy();
        this.executionStrategyFactory = this.initExecutionStrategyFactory();
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

    public ExceptionHandler getDefaultExceptionHandler() {
        return defaultExceptionHandler;
    }

    public void setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {
        this.defaultExceptionHandler = exceptionHandler;
    }

    public ExceptionHandlerFactory getExceptionHandlerFactory() {
        return exceptionHandlerFactory;
    }

    public void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    public ExecutionStrategy getDefaultExecutionStrategy() {
        return defaultExecutionStrategy;
    }

    public void setDefaultExecutionStrategy(ExecutionStrategy defaultExecutionStrategy) {
        this.defaultExecutionStrategy = defaultExecutionStrategy;
    }

    public ExecutionStrategyFactory getExecutionStrategyFactory() {
        return executionStrategyFactory;
    }

    public void setExecutionStrategyFactory(ExecutionStrategyFactory executionStrategyFactory) {
        this.executionStrategyFactory = executionStrategyFactory;
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
