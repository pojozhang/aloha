package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.util.Assert;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

public class AnnotatedListenerResolver implements ListenerResolver {

    private static final Map<Class<? extends Annotation>, ListenerAnnotationResolver> DEFAULT_RESOLVERS;

    static {
        DEFAULT_RESOLVERS = new ConcurrentHashMap<>();
        DEFAULT_RESOLVERS.put(Consume.class, new ConsumerAnnotationResolver());
        DEFAULT_RESOLVERS.put(Subscribe.class, new SubscriberAnnotationResolver());
    }

    private final Map<Class<? extends Annotation>, ListenerAnnotationResolver> listenerAnnotationResolvers;

    public AnnotatedListenerResolver() {
        this(DEFAULT_RESOLVERS);
    }

    public AnnotatedListenerResolver(Map<Class<? extends Annotation>, ListenerAnnotationResolver> listenerAnnotationResolvers) {
        this.listenerAnnotationResolvers = new ConcurrentHashMap<>();
        this.listenerAnnotationResolvers.putAll(listenerAnnotationResolvers);
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
                        ListenerAnnotationResolver listenerAnnotationResolver = this.listenerAnnotationResolvers.get(annotationClass);
                        Assert.notNull(listenerAnnotationResolver, "No listener annotation resolver found for " + annotationClass);
                        Annotation annotation = method.getAnnotation(annotationClass);
                        return listenerAnnotationResolver.resolve(container, annotation, method, messageBus);
                    }).collect(toSet()));
        });
        return listeners;
    }

    protected Collection<Class<? extends Annotation>> getSupportedListenerAnnotations() {
        return this.listenerAnnotationResolvers.keySet();
    }
}
