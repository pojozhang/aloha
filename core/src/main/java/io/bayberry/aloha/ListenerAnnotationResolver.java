package io.bayberry.aloha;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface ListenerAnnotationResolver<A extends Annotation> {

    Listener resolve(Object container, A annotation, Method method, MessageBus messageBus);
}
