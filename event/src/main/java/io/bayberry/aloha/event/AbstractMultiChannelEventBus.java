package io.bayberry.aloha.event;

import com.google.common.collect.Maps;
import io.bayberry.aloha.event.annotation.Subscribe;

import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractMultiChannelEventBus implements MultiChannelEventBus {

    protected Map<String, List<Invocation>> channelInvocationsMapping = Maps.newConcurrentMap();

    public void register(Object subscriber) {
        iterateSubscriberMethods(subscriber, this::addToChannelInvocationsMapping);
    }

    private void iterateSubscriberMethods(Object subscriber, SubscriberMethodsIteratorCallback callback) {
        Method[] methods = subscriber.getClass().getMethods();
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Subscribe.class))
                .forEach(method -> {
                    Subscribe subscribe = method.getAnnotation(Subscribe.class);
                    String channel = subscribe.channel();
                    if (channel.length() == 0) {
                        channel = this.getChannel(method.getParameterTypes()[0]);
                    }
                    callback.call(subscriber, method, channel);
                });
    }

    private void addToChannelInvocationsMapping(Object subscriber, Method method, String channel) {
        Invocation invocation = new Invocation(subscriber, method);
        List<Invocation> invocations = channelInvocationsMapping.getOrDefault(channel, new ArrayList<>());
        invocations.add(invocation);
        channelInvocationsMapping.put(channel, invocations);
    }

    @Override
    public void unregister(Object subscriber) {
        channelInvocationsMapping.values().forEach(invocations -> {
            invocations.forEach(invocation -> {
                if (invocation.getSubscriber() == subscriber) {
                    invocations.remove(invocation);
                }
            });
        });
    }

    @FunctionalInterface
    private interface SubscriberMethodsIteratorCallback {

        void call(Object subscriber, Method method, String channel);
    }
}
