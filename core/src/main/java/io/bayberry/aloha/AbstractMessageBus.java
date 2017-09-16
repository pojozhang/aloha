package io.bayberry.aloha;

import io.bayberry.aloha.support.*;

public abstract class AbstractMessageBus extends LifeCycleContext implements MessageBus {

    private ChannelResolver channelResolver;
    private ListenerResolver listenerResolver;
    private ListenerRegistry listenerRegistry;
    private StreamRegistry streamRegistry;
    private ExceptionHandler defaultExceptionHandler;
    private ExceptionHandlerFactory exceptionHandlerFactory;
    private ExecutionStrategy defaultExecutionStrategy;
    private ExecutionStrategyFactory executionStrategyFactory;

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
        this.streamRegistry = this.initStreamRegistry();
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
            if (listener instanceof Consumer) {
                Stream stream = this.bindStream(listener);
                stream.register(listener);
                this.getStreamRegistry().register(stream);
            }//TODO subscriber
        });
        this.getStreamRegistry().getStreams().forEach(Stream::start);
    }

    @Override
    public void onDestroy() {
        this.getStreamRegistry().getStreams().forEach(Stream::stop);
    }

    protected StreamRegistry getStreamRegistry() {
        return streamRegistry;
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
    public Channel resolveChannel(Class messageType) {
        return this.getChannelResolver().resolve(messageType);
    }

    protected ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    protected ListenerResolver getListenerResolver() {
        return listenerResolver;
    }

    protected ChannelResolver getChannelResolver() {
        return channelResolver;
    }

    protected ListenerRegistry initListenerRegistry() {
        return new DefaultListenerRegistry();
    }

    protected ListenerResolver initListenerResolver() {
        return new DefaultListenerResolver();
    }

    protected StreamRegistry initStreamRegistry() {
        return new DefaultStreamRegistry();
    }

    protected ChannelResolver initChannelResolver() {
        return new DefaultChannelResolver();
    }

    protected ExceptionHandler initDefaultExceptionHandler() {
        return new DefaultLogExceptionHandler();
    }

    protected ExceptionHandlerFactory initExceptionHandlerFactory() {
        return new DefaultExceptionHandlerFactory();
    }

    protected abstract ExecutionStrategy initDefaultExecutionStrategy();

    protected abstract ExecutionStrategyFactory initExecutionStrategyFactory();

    protected abstract Stream bindStream(Listener listener);
}
