package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;

import java.lang.reflect.Method;

public class ConsumerAnnotationResolver implements ListenerAnnotationResolver<Consume> {

    @Override
    public Listener resolve(Object container, Consume annotation, Method method, MessageBus messageBus) {
        return new Consumer(container, method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }
}
