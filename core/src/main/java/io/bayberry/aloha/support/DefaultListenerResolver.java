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
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DefaultListenerResolver implements ListenerResolver {

    private static Set<Class<? extends Annotation>> SUPPORTED_LISTENER_ANNOTATION_CLASSES;

    static {
        SUPPORTED_LISTENER_ANNOTATION_CLASSES = new HashSet<>();
        SUPPORTED_LISTENER_ANNOTATION_CLASSES.add(Consume.class);
        SUPPORTED_LISTENER_ANNOTATION_CLASSES.add(Subscribe.class);
    }

    @Override
    public Set<Listener> resolve(Object container, MessageBus messageBus) {
        Set<Class<? extends Annotation>> supportedListenerAnnotationClasses = this.getSupportedListenerAnnotations();
        Assert.notEmpty(supportedListenerAnnotationClasses, "Fail to resolve listeners: no supported annotation");
        Set<Listener> listeners = new HashSet<>();
        supportedListenerAnnotationClasses.forEach(annotationClass -> {
            listeners.addAll(Arrays.stream(container.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(annotationClass))
                    .map(method -> {
                        Annotation annotation = method.getAnnotation(annotationClass);
                        Method resolverMethod = Reflection.getDeclaredMethod(annotationClass, "resolver").orElseThrow(() -> new AlohaException("Annotation must contains resolver() method"));
                        try {
                            Class<? extends AnnotatedListenerResolver> resolverClass = (Class<? extends AnnotatedListenerResolver>) resolverMethod.invoke(annotation);
                            Listener listener = resolverClass.newInstance().resolve(container, annotation, method, messageBus);
                            this.afterResolveListener(listener);
                            return listener;
                        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                            throw new AlohaException(e);
                        }
                    }).collect(toSet()));
        });
        return listeners;
    }

    protected Set<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return SUPPORTED_LISTENER_ANNOTATION_CLASSES;
    }

    protected void afterResolveListener(Listener listener) {
    }
}
