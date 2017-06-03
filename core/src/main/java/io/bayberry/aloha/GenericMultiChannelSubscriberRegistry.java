package io.bayberry.aloha;

import com.google.common.collect.Maps;
import io.bayberry.aloha.annotation.Subscribe;

import java.lang.reflect.Method;
import java.util.*;

public class GenericMultiChannelSubscriberRegistry implements MultiChannelSubscriberRegistry {

    private final Map<String, List<Subscriber>> channelSubscribersMapping = Maps.newConcurrentMap();
    private final MultiChannelEventBus eventBus;

    public GenericMultiChannelSubscriberRegistry(MultiChannelEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        iterateSubscriberMethods(subscriber, this::addToChannelInvocationsMapping);
    }

    @Override
    public void unregister(Object subscriber) {
        channelSubscribersMapping.values().forEach(subscribers -> {
            subscribers.forEach(sub -> {
                if (sub.getTarget() == subscriber) {
                    subscribers.remove(sub);
                }
            });
        });
    }

    private void iterateSubscriberMethods(Object subscriber, SubscriberMethodsIteratorCallback callback) {
        Method[] methods = subscriber.getClass().getMethods();
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Subscribe.class))
                .forEach(method -> {
                    Subscribe subscribe = method.getAnnotation(Subscribe.class);
                    String channel = subscribe.channel();
                    if (channel.length() == 0) {
                        channel = this.eventBus.resolveChannel(method.getParameterTypes()[0]);
                    }
                    callback.call(subscriber, method, channel);
                });
    }

    private void addToChannelInvocationsMapping(Object subscriber, Method method, String channel) {
        Subscriber sub = new GenericSubscriber(subscriber, method, this.eventBus.getDeserializer());
        List<Subscriber> subscribers = channelSubscribersMapping.getOrDefault(channel, new ArrayList<>());
        subscribers.add(sub);
        channelSubscribersMapping.put(channel, subscribers);
    }

    @Override
    public Set<String> getChannels() {
        return this.channelSubscribersMapping.keySet();
    }

    @Override
    public List<Subscriber> getSubscribers(String channel) {
        return this.channelSubscribersMapping.get(channel);
    }

    @FunctionalInterface
    private interface SubscriberMethodsIteratorCallback {

        void call(Object subscriber, Method method, String channel);
    }
}
