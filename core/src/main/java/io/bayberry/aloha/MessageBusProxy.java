package io.bayberry.aloha;

public class MessageBusProxy implements MessageBus {

//    static <T> T proxy(Class<T> proxyClass, MessageBus... messageBuses) {
//
//    }

    @Override
    public void post(Message message) {

    }

    @Override
    public void register(Object container) {

    }

    @Override
    public void unregister(Object container) {

    }

    @Override
    public ExceptionHandler getDefaultExceptionHandler() {
        return null;
    }

    @Override
    public void setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {

    }

    @Override
    public ExceptionHandlerFactory getExceptionHandlerFactory() {
        return null;
    }

    @Override
    public void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {

    }

    @Override
    public ExecutionStrategy getDefaultExecutionStrategy() {
        return null;
    }

    @Override
    public void setDefaultExecutionStrategy(ExecutionStrategy defaultExecutionStrategy) {

    }

    @Override
    public ExecutionStrategyFactory getExecutionStrategyFactory() {
        return null;
    }

    @Override
    public void setExecutionStrategyFactory(ExecutionStrategyFactory executionStrategyFactory) {

    }

    @Override
    public ListenerRegistry getListenerRegistry() {
        return null;
    }

    @Override
    public ListenerResolver getListenerResolver() {
        return null;
    }

    @Override
    public ChannelResolver getChannelResolver() {
        return null;
    }

    @Override
    public ReceiverRegistry getReceiverRegistry() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
