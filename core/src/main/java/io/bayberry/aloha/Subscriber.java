package io.bayberry.aloha;

import java.lang.reflect.Method;

public class Subscriber extends AbstractListener {

    protected Subscriber(Object container, Method method, MessageBus messageBus,
                         ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.container = container;
        this.method = method;
        this.messageBus = messageBus;
        this.exceptionHandler = exceptionHandler;
        this.executionStrategy = executionStrategy;
    }
}
