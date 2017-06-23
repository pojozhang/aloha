package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberResolver;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.util.Reflection;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRemoteSubscriberResolver implements SubscriberResolver {

    @Override
    public List<Subscriber> resolve(Object target, EventBus eventBus) {
        return new Reflection().getMethodsWithAnnotation(target, Subscribe.class)
            .map(method -> {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                String channel = subscribe.channel();
                if (channel == null || channel.length() == 0) {
                    channel = eventBus.getChannelResolver().resolve(method.getParameterTypes()[0]);
                }
                return new GenericSubscriber(channel, target, method,
                    eventBus.getExceptionHandlerProvider().provide(subscribe.exceptionHandler()),
                    new GenericSubscriberOptions(subscribe));
            }).collect(Collectors.toList());
    }
}
