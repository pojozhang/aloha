package io.bayberry.aloha;

import io.bayberry.aloha.support.*;

public abstract class AbstractMessageBus extends LifeCycleContext implements MessageBus {

    private ChannelResolver channelResolver;
    private ListenerResolver listenerResolver;
    private ListenerRegistry listenerRegistry;
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
    public void register(Object container) {
        this.listenerRegistry.register(this.listenerResolver.resolve(container, this));
    }

    @Override
    public void unregister(Object container) {
        this.listenerRegistry.unregister(this.listenerResolver.resolve(container, this));
    }

    @Override
    protected void onCreate() {
        this.listenerRegistry = this.initListenerRegistry();
        this.receiverRegistry = this.initReceiverRegistry();
        this.listenerResolver = this.initListenerResolver();
        this.channelResolver = this.initChannelResolver();
        this.defaultExceptionHandler = this.initDefaultExceptionHandler();
        this.exceptionHandlerFactory = this.initExceptionHandlerFactory();
        this.defaultExecutionStrategy = this.initDefaultExecutionStrategy();
        this.executionStrategyFactory = this.initExecutionStrategyFactory();
    }

    @Override
    public void onStart() {
        this.listenerRegistry.getListeners().forEach(listener -> {
            Receiver receiver = this.bindReceiver(listener);
            this.getReceiverRegistry().register(receiver);
            receiver.start();
        });
    }

    @Override
    public void onDestroy() {
        this.getReceiverRegistry().getReceivers().forEach(Receiver::stop);
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
    public ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    @Override
    public ListenerResolver getListenerResolver() {
        return listenerResolver;
    }

    @Override
    public ChannelResolver getChannelResolver() {
        return channelResolver;
    }

    protected ListenerRegistry initListenerRegistry() {
        return new DefaultListenerRegistry();
    }

    protected ListenerResolver initListenerResolver() {
        return new DefaultListenerResolver();
    }

    protected ReceiverRegistry initReceiverRegistry() {
        return new DefaultReceiverRegistry();
    }

    protected ChannelResolver initChannelResolver() {
        return new DefaultChannelResolver();
    }

    protected abstract ExceptionHandler initDefaultExceptionHandler();

    protected ExceptionHandlerFactory initExceptionHandlerFactory() {
        return new DefaultExceptionHandlerFactory();
    }

    protected abstract ExecutionStrategy initDefaultExecutionStrategy();

    protected abstract ExecutionStrategyFactory initExecutionStrategyFactory();

    protected abstract Receiver bindReceiver(Listener listener);
}
