package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Subscribe;

import java.lang.reflect.Method;

public class AnnotatedSubscriberResolver implements AnnotatedListenerResolver<Subscribe> {

    @Override
    public Listener resolve(Object container, Subscribe annotation, Method method, Channel channel, MessageBus messageBus) {
        return new Subscriber(container, channel, method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }
}
