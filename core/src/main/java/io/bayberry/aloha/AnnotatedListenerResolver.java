package io.bayberry.aloha;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface AnnotatedListenerResolver<A extends Annotation> {

    Listener resolve(Object container, A annotation, Method method, Channel channel, MessageBus messageBus);
}
