package io.bayberry.aloha;

import io.bayberry.aloha.exception.UnsupportedListenerException;
import io.bayberry.aloha.support.DefaultExceptionHandlerFactory;
import io.bayberry.aloha.support.DefaultLogExceptionHandler;
import io.bayberry.aloha.support.DefaultStreamRegistry;
import io.bayberry.aloha.support.SimpleChannelResolver;
import io.bayberry.aloha.util.Assert;
import io.bayberry.aloha.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class AbstractMessageBus extends LifeCycleContext implements MessageBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageBus.class);
    private String name;
    private ChannelResolver channelResolver;
    private ListenerResolver listenerResolver;
    private StreamRegistry streamRegistry;
    private ExceptionHandler defaultExceptionHandler;
    private ExceptionHandlerFactory exceptionHandlerFactory;
    private ExecutionStrategy defaultExecutionStrategy;
    private ExecutionStrategyFactory executionStrategyFactory;

    protected AbstractMessageBus(String name) {
        this.name = name;
    }

    @Override
    public void register(Object container) {
        Set<Listener> listeners = this.listenerResolver.resolve(container, this);
        listeners.forEach(listener -> {
            Channel channel = this.resolveChannel(listener);
            try {
                Stream stream = this.bindStream(channel, listener);
                stream.register(listener);
                this.getStreamRegistry().register(stream);
            } catch (UnsupportedListenerException e) {
                LOGGER.error("Fail to bind stream", e);
            }
        });
    }

    @Override
    public void unregister(Object container) {
        Set<Listener> listeners = this.listenerResolver.resolve(container, this);
        listeners.forEach(listener -> listener.getStream().unregister(listener));
    }

    @Override
    protected void onCreate() {
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
        this.getStreamRegistry().getStreams().forEach(Stream::start);
    }

    @Override
    public void onStop() {
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
    public Channel resolveOutboundChannel(Class messageType) {
        return this.getChannelResolver().resolve(messageType);
    }

    protected Channel resolveChannel(Listener listener) {
        io.bayberry.aloha.annotation.Channel channel = listener.getMethod().getAnnotation(io.bayberry.aloha.annotation.Channel.class);
        if (channel != null && !Strings.isNullOrEmpty(channel.value())) return Channel.valueOf(channel.value());
        return this.resolveOutboundChannel(Assert.notNull(listener.getMessageType(), "Fail to resolve channel of listener"));
    }

    public String getName() {
        return name;
    }

    protected ListenerResolver getListenerResolver() {
        return listenerResolver;
    }

    protected ChannelResolver getChannelResolver() {
        return channelResolver;
    }

    protected ListenerResolver initListenerResolver() {
        return new AnnotatedListenerResolver();
    }

    protected StreamRegistry initStreamRegistry() {
        return new DefaultStreamRegistry();
    }

    protected ChannelResolver initChannelResolver() {
        return new SimpleChannelResolver();
    }

    protected ExceptionHandler initDefaultExceptionHandler() {
        return new DefaultLogExceptionHandler();
    }

    protected ExceptionHandlerFactory initExceptionHandlerFactory() {
        return new DefaultExceptionHandlerFactory();
    }

    protected abstract ExecutionStrategy initDefaultExecutionStrategy();

    protected abstract ExecutionStrategyFactory initExecutionStrategyFactory();

    protected abstract Stream bindStream(Channel channel, Listener listener) throws UnsupportedListenerException;
}
