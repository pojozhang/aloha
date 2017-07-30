package io.bayberry.aloha;

import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.util.Reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotatedConsumerResolver implements AnnotatedListenerResolver<Consume> {

    @Override
    public Listener resolve(Object container, Consume annotation, Method method, Channel channel, MessageBus messageBus) {
        return new Consumer(container, channel, method, messageBus, messageBus.getDefaultExceptionHandler(), messageBus.getDefaultExecutionStrategy());
    }
}
