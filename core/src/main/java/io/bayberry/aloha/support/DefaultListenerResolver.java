package io.bayberry.aloha.support;

import com.google.common.collect.Sets;
import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerResolver;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultListenerResolver implements ListenerResolver {

    private static Set<Class<? extends Annotation>> SUPPORTED_LISTENER_ANNOTATIONS;

    static {
        SUPPORTED_LISTENER_ANNOTATIONS = new HashSet<>();
        SUPPORTED_LISTENER_ANNOTATIONS.add(Consume.class);
        SUPPORTED_LISTENER_ANNOTATIONS.add(Subscribe.class);
    }

    @Override
    public List<Listener> resolve(Object container, MessageBus messageBus) {
        Set<? extends Annotation> supportedListenerAnnotations = messageBus.getSupportedListenerAnnotations();
        Assert.notEmpty(supportedListenerAnnotations, "Fail to resolve listeners: no supported annotation");
        supportedListenerAnnotations.forEach(annotation -> {
            Arrays.stream(container.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(annotation) || method.isAnnotationPresent(Subscribe.class))
                    .forEach(method -> {
                        Channel channel = messageBus.getChannelResolver().resolve(method);
                        if (!channel.isResolved()) {
                            throw new AlohaException("Fail to resolve listener: channel is not resolved");
                        }
                    });
        });
    }
}
