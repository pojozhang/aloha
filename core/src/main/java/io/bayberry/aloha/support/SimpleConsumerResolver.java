package io.bayberry.aloha.support;

import io.bayberry.aloha.Consumer;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.annotation.Consume;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

public class SimpleConsumerResolver extends DefaultListenerResolver {

    protected Collection<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return Collections.singleton(Consume.class);
    }

    protected Collection<Class<? extends Listener>> getSupportedListenerClasses() {
        return Collections.singleton(Consumer.class);
    }
}
