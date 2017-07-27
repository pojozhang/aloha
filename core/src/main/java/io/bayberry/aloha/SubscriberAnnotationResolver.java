package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.Reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriberAnnotationResolver extends AbstractListenerAnnotationResolver<Subscribe> {

    @Override
    public List<Listener> resolve(Object container, MessageBus messageBus) {

        return new Reflection().getMethodsWithAnnotation(container.getClass(), Consume.class).stream()
                .map(method -> {
                    Consume subscribe = method.getAnnotation(Consume.class);
                    Channel channel = getResolvedChannel(subscribe, method, messageBus);
                    return new GenericSubscriber(channel, container, method, messageBus,
                            messageBus.getExceptionHandlerFactory().provide(subscribe.exceptionHandler()),
                            messageBus.getExecutionStrategyFactory().provide(subscribe.executionStrategy()));
                }).collect(Collectors.toList());
    }

    protected Channel getResolvedChannel(Consume subscribe, Method method, MessageBus messageBus) {
        Channel channel = new Channel(subscribe.channel());
        if (channel.getName() == null || channel.getName().length() == 0) {
            channel = messageBus.getChannelResolver().resolve(method.getParameterTypes()[0]);
        }
        return channel;
    }
}
