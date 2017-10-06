package io.bayberry.aloha;

import java.lang.reflect.Method;

public class Consumer extends AbstractListener {

    protected Consumer(Object container, Method method, MessageBus messageBus,
                       ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.container = container;
        this.method = method;
        this.messageBus = messageBus;
        this.exceptionHandler = exceptionHandler;
        this.executionStrategy = executionStrategy;
    }
}
