package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberResolver;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.util.Reflection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericSubscriberResolver implements SubscriberResolver {

    @Override
    public List<Subscriber> resolve(Object target, EventBus eventBus) {
        return new Reflection().getMethodsWithAnnotation(target, Subscribe.class)
                .map(method -> {
                    Subscribe subscribe = method.getAnnotation(Subscribe.class);
                    List<String> channels;
                    String channel = subscribe.channel();
                    if (channel == null || channel.length() == 0) {
                        channels = eventBus.getChannelResolver().resolve(method.getParameterTypes()[0]);
                    } else {
                        channels = Arrays.asList(channel);
                    }
                    return new GenericSubscriber(channels, target, method, eventBus,
                            eventBus.getExceptionHandlerFactory().provide(subscribe.exceptionHandler()),
                            eventBus.getExecutionStrategyFactory().provide(subscribe.executionStrategy()));
                }).collect(Collectors.toList());
    }
}
