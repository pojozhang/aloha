package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.Reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotatedConsumerResolver extends AnnotatedListenerResolver<Consume> {

    @Override
    public Listener resolve(Object container, Consume annotation, Method method, MessageBus messageBus) {
        return new Consumer(container, resolveChannel(annotation, method, messageBus), method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }

    protected Channel resolveChannel(Consume annotation, Method method, MessageBus messageBus) {
        String channelName = annotation.channel();
        if (channelName != null && channelName.trim().length() > 0) {
            return new Channel(channelName);
        }
        return super.resolve(method, messageBus);
    }
}
