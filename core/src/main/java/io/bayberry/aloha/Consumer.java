package io.bayberry.aloha;

import java.lang.reflect.Method;

public class Consumer extends AbstractListener {

    protected Consumer(Object container, Channel channel, Method method, MessageBus messageBus,
                       ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.container = container;
        this.channel = channel;
        this.method = method;
        this.messageBus = messageBus;
        this.exceptionHandler = exceptionHandler;
        this.executionStrategy = executionStrategy;
    }
}
