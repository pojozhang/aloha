package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.util.Assert;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

public class AnnotatedListenerResolver implements ListenerResolver {

    private final Map<Class<? extends Annotation>, ListenerAnnotationResolver> listenerAnnotationResolvers;

    public AnnotatedListenerResolver() {
        this.listenerAnnotationResolvers = new ConcurrentHashMap<>();
        this.listenerAnnotationResolvers.put(Consume.class, new ConsumerAnnotationResolver());
        this.listenerAnnotationResolvers.put(Subscribe.class, new SubscriberAnnotationResolver());
    }

    public Map<Class<? extends Annotation>, ListenerAnnotationResolver> getListenerAnnotationResolvers() {
        return listenerAnnotationResolvers;
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
                        ListenerAnnotationResolver listenerAnnotationResolver = this.listenerAnnotationResolvers.get(annotation);
                        Assert.notNull(listenerAnnotationResolver, "No listener annotation resolver found for " + annotation);
                        return listenerAnnotationResolver.resolve(container, annotation, method, messageBus);
                    }).collect(toSet()));
        });
        return listeners;
    }

    protected Collection<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return this.listenerAnnotationResolvers.keySet();
    }
}
