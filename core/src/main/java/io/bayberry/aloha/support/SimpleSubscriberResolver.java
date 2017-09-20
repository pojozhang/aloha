package io.bayberry.aloha.support;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.annotation.Subscribe;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

public class SimpleSubscriberResolver extends DefaultListenerResolver {

    protected Collection<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return Collections.singleton(Subscribe.class);
    }

    protected Collection<Class<? extends Listener>> getSupportedListenerClasses() {
        return Collections.singleton(Subscriber.class);
    }
}
