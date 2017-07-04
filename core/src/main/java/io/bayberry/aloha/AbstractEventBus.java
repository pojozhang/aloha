package io.bayberry.aloha;

public abstract class AbstractEventBus extends LifeCycleContext implements EventBus {

    private ChannelResolver channelResolver;
    private SubscriberResolver subscriberResolver;
    private SubscriberRegistry subscriberRegistry;
    private ListenerRegistry listenerRegistry;
    private ExceptionHandler defaultExceptionHandler;
    private ExceptionHandlerFactory exceptionHandlerFactory;
    private ExecutionStrategy defaultExecutionStrategy;
    private ExecutionStrategyFactory executionStrategyFactory;

    @Override
    public void post(Object event) {
        this.post(this.getChannelResolver().resolve(event.getClass()), event);
    }

    @Override
    public void register(Object target) {
        this.subscriberRegistry.register(this.subscriberResolver.resolve(target, this));
    }

    @Override
    public void unregister(Object target) {
        this.subscriberRegistry.unregister(this.subscriberResolver.resolve(target, this));
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

    @Override
    public ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    @Override
    public ExceptionHandler getDefaultExceptionHandler() {
        return defaultExceptionHandler;
    }

    @Override
    public void setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {
        this.defaultExceptionHandler = exceptionHandler;
    }

    @Override
    public ExceptionHandlerFactory getExceptionHandlerFactory() {
        return exceptionHandlerFactory;
    }

    @Override
    public void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    @Override
    public ExecutionStrategy getDefaultExecutionStrategy() {
        return defaultExecutionStrategy;
    }

    @Override
    public void setDefaultExecutionStrategy(ExecutionStrategy defaultExecutionStrategy) {
        this.defaultExecutionStrategy = defaultExecutionStrategy;
    }

    @Override
    public ExecutionStrategyFactory getExecutionStrategyFactory() {
        return executionStrategyFactory;
    }

    @Override
    public void setExecutionStrategyFactory(ExecutionStrategyFactory executionStrategyFactory) {
        this.executionStrategyFactory = executionStrategyFactory;
    }

    @Override
    public SubscriberRegistry getSubscriberRegistry() {
        return subscriberRegistry;
    }

    @Override
    public SubscriberResolver getSubscriberResolver() {
        return subscriberResolver;
    }

    @Override
    public ChannelResolver getChannelResolver() {
        return channelResolver;
    }
}
