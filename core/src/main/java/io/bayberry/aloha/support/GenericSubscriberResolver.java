package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberResolver;
import io.bayberry.aloha.annotation.Channel;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.util.Reflection;

import java.util.List;
import java.util.stream.Collectors;

public class GenericSubscriberResolver implements SubscriberResolver {

    @Override
    public List<Subscriber> resolve(Object subscriber, EventBus eventBus) {
        return new Reflection().getMethodsWithAnnotation(subscriber, Subscribe.class)
                .map(method -> {
                    Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                    Channel channelAnnotation = method.getAnnotation(Channel.class);
                    String channel;
                    if (channelAnnotation != null) {
                        channel = channelAnnotation.value();
                    } else {
                        channel = eventBus.getChannelResolver().resolve(method.getParameterTypes()[0]);
                    }
                    return new GenericSubscriber(subscriber, method, channel, eventBus.getExceptionHandlerProvider().provide(subscribeAnnotation.exceptionHandler()),GenericSubscriber.Settings.parse(subscribeAnnotation));
                }).collect(Collectors.toList());
    }
}
