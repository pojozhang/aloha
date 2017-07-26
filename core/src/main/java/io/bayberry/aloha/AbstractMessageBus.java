package io.bayberry.aloha;

public abstract class AbstractMessageBus extends LifeCycleContext implements MessageBus {

    private ChannelResolver channelResolver;
    private SubscriberResolver subscriberResolver;
    private SubscriberRegistry subscriberRegistry;
    private ReceiverRegistry receiverRegistry;
    private ExceptionHandler defaultExceptionHandler;
    private ExceptionHandlerFactory exceptionHandlerFactory;
    private ExecutionStrategy defaultExecutionStrategy;
    private ExecutionStrategyFactory executionStrategyFactory;

    @Override
    public void produce(Object message) {
        this.produce(this.getChannelResolver().resolve(message.getClass()), message);
    }

    @Override
    public void publish(Object message) {
        this.publish(this.getChannelResolver().resolve(message.getClass()), message);
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
        this.receiverRegistry = this.initReceiverRegistry();
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
            Receiver listener = this.bindListener(channel);
            listener.register(this.subscriberRegistry.getSubscribers(channel));
            this.getReceiverRegistry().register(listener);
            listener.start();
        });
    }

    @Override
    public void onDestroy() {
        this.getReceiverRegistry().getReceivers().forEach(Receiver::shutdown);
    }

    @Override
    public ReceiverRegistry getReceiverRegistry() {
        return receiverRegistry;
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
