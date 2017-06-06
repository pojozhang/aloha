package io.bayberry.aloha.support;

import com.google.common.collect.Maps;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericSubscriberRegistry implements SubscriberRegistry {

    private final Map<String, List<Subscriber>> channelSubscribers = Maps.newConcurrentMap();

    @Override
    public void register(List<Subscriber> subscribers) {
        subscribers.forEach(this::register);
    }

    @Override
    public void register(Subscriber subscriber) {
        List<Subscriber> subscribers = this.channelSubscribers.getOrDefault(subscriber.getChannel(), new ArrayList<>());
        subscribers.add(subscriber);
        this.channelSubscribers.put(subscriber.getChannel(), subscribers);
    }

    @Override
    public void unregister(List<Subscriber> subscribers) {
        subscribers.forEach(this::unregister);
    }

    @Override
    public void unregister(Subscriber subscriber) {
        this.channelSubscribers.values().forEach(subscribers -> subscribers.forEach(sub -> {
            if (sub.getTarget() == subscriber) {
                subscribers.remove(sub);
            }
        }));
    }

    @Override
    public Set<String> getChannels() {
        return this.channelSubscribers.keySet();
    }

    @Override
    public List<Subscriber> getSubscribers(String channel) {
        return this.channelSubscribers.get(channel);
    }
}
