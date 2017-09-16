package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlohaException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AnnotatedListenerResolver<A extends Annotation> {

    public abstract Listener resolve(Object container, A annotation, Method method, MessageBus messageBus);

    protected Channel resolve(Method method, MessageBus messageBus) {
        if (method.getParameterTypes().length > 0) {
            return messageBus.resolveChannel(method.getParameterTypes()[0]);
        } else {
            throw new AlohaException("Fail to resolve subscriber: channel is empty");
        }
    }
}
