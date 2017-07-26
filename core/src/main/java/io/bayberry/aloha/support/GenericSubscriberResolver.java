package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberResolver;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.util.Reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class GenericSubscriberResolver implements SubscriberResolver {

    @Override
    public List<Subscriber> resolve(Object target, MessageBus messageBus) {
        return new Reflection().getMethodsWithAnnotation(target.getClass(), Consume.class).stream()
                .map(method -> {
                    Consume subscribe = method.getAnnotation(Consume.class);
                    Channel channel = getResolvedChannel(subscribe, method, messageBus);
                    return new GenericSubscriber(channel, target, method, messageBus,
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
