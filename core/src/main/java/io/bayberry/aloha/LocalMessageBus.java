package io.bayberry.aloha;

import io.bayberry.aloha.support.DefaultReceiverRegistry;
import io.bayberry.aloha.support.DefaultChannelResolver;
import io.bayberry.aloha.support.DefaultExceptionHandlerFactory;
import io.bayberry.aloha.support.GenericExecutionStrategy;
import io.bayberry.aloha.support.GenericExecutionStrategyFactory;
import io.bayberry.aloha.support.DefaultListenerRegistry;
import io.bayberry.aloha.support.LogExceptionHandler;

public abstract class LocalMessageBus extends AbstractMessageBus {

    @Override
    public ExceptionHandler initDefaultExceptionHandler() {
        return new LogExceptionHandler();
    }

    @Override
    public ExecutionStrategy initDefaultExecutionStrategy() {
        return new GenericExecutionStrategy();
    }

    @Override
    public ExecutionStrategyFactory initExecutionStrategyFactory() {
        return new GenericExecutionStrategyFactory();
    }

    @Override
    public void onDestroy() {
        this.getReceiverRegistry().getReceivers().forEach(Receiver::stop);
    }
}
