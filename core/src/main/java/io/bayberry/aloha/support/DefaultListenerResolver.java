package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.Assert;
import io.bayberry.aloha.util.Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DefaultListenerResolver implements ListenerResolver {

    private static Set<Class<? extends Annotation>> DEFAULT_SUPPORTED_LISTENER_ANNOTATION_CLASSES;
    private static Set<Class<? extends Listener>> DEFAULT_SUPPORTED_LISTENER_CLASSES;

    static {
        DEFAULT_SUPPORTED_LISTENER_ANNOTATION_CLASSES = new HashSet<>();
        DEFAULT_SUPPORTED_LISTENER_ANNOTATION_CLASSES.add(Consume.class);
        DEFAULT_SUPPORTED_LISTENER_ANNOTATION_CLASSES.add(Subscribe.class);

        DEFAULT_SUPPORTED_LISTENER_CLASSES = new HashSet<>();
        DEFAULT_SUPPORTED_LISTENER_CLASSES.add(Consumer.class);
        DEFAULT_SUPPORTED_LISTENER_CLASSES.add(Subscriber.class);
    }

    @Override
    public Set<Listener> resolve(Object container, MessageBus messageBus) {
        Collection<Class<? extends Annotation>> supportedListenerAnnotationClasses = this.getSupportedListenerAnnotations();
        Assert.notEmpty(supportedListenerAnnotationClasses, "Fail to resolve listeners: no supported annotation");
        Set<Listener> listeners = new HashSet<>();
        supportedListenerAnnotationClasses.forEach(annotationClass -> {
            listeners.addAll(Arrays.stream(container.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(annotationClass))
                    .map(method -> {
                        Annotation annotation = method.getAnnotation(annotationClass);
                        Method resolverMethod = Reflection.getDeclaredMethod(annotationClass, "resolver").orElseThrow(() -> new AlohaException("Annotation must contains annotationResolver() method"));
                        try {
                            Class<? extends AnnotatedListenerResolver> resolverClass = (Class<? extends AnnotatedListenerResolver>) resolverMethod.invoke(annotation);
                            Listener listener = resolverClass.newInstance().resolve(container, annotation, method, messageBus);
                            Assert.isTrue(this.validateListener(listener), "Unsupported listener type: " + listener.getClass());
                            this.afterResolveListener(listener);
                            return listener;
                        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                            throw new AlohaException(e);
                        }
                    }).collect(toSet()));
        });
        return listeners;
    }

    protected boolean validateListener(Listener listener) {
        for (Class<? extends Listener> type : this.getSupportedListenerClasses()) {
            if (type.isAssignableFrom(listener.getClass())) return true;
        }
        return false;
    }

    protected Collection<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return DEFAULT_SUPPORTED_LISTENER_ANNOTATION_CLASSES;
    }

    protected Collection<Class<? extends Listener>> getSupportedListenerClasses() {
        return DEFAULT_SUPPORTED_LISTENER_CLASSES;
    }

    protected void afterResolveListener(Listener listener) {
    }
}
