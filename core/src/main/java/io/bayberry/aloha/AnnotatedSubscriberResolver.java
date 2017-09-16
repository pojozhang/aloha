package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Subscribe;

import java.lang.reflect.Method;

public class AnnotatedSubscriberResolver extends AnnotatedListenerResolver<Subscribe> {

    @Override
    public Listener resolve(Object container, Subscribe annotation, Method method, MessageBus messageBus) {
        return new Subscriber(container, resolveChannel(annotation, method, messageBus), method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }

    protected Channel resolveChannel(Subscribe annotation, Method method, MessageBus messageBus) {
        String channelName = annotation.channel();
        if (channelName != null && channelName.trim().length() > 0) {
            return new Channel(channelName);
        }
        return super.resolve(method, messageBus);
    }
}
