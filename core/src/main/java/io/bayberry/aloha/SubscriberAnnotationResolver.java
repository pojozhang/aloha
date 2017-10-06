package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Subscribe;

import java.lang.reflect.Method;

public class SubscriberAnnotationResolver implements ListenerAnnotationResolver<Subscribe> {

    @Override
    public Listener resolve(Object container, Subscribe annotation, Method method, MessageBus messageBus) {
        return new Subscriber(container, method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }
}
