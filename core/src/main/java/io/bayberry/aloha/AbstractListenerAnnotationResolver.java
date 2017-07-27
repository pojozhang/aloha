package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractListenerAnnotationResolver<A extends Annotation> implements ListenerResolver {

    @Override
    public List<Listener> resolve(Object container, MessageBus messageBus) {
        Arrays.stream(container.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Consume.class) || method.isAnnotationPresent(Subscribe.class))
                .forEach(method -> {
                    Channel channel = messageBus.getChannelResolver().resolve(method);
                    if (!channel.isResolved()) {
                        throw new AlohaException("Fail to resolve listener: channel is not resolved");
                    }
                });
    }
}
