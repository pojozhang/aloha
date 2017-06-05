package io.bayberry.aloha.support;

import com.google.common.collect.Maps;
import io.bayberry.aloha.ChannelResolver;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.SubscriberRegistry;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericSubscriberRegistry implements SubscriberRegistry {

    private final Map<String, List<Subscriber>> channelSubscribers = Maps.newConcurrentMap();
    private final ChannelResolver channelResolver;

    public GenericSubscriberRegistry(final ChannelResolver channelResolver) {
        this.channelResolver = channelResolver;
    }

    @Override
    public void register(List<Subscriber> subscribers) {
        subscribers.forEach(this::register);
    }

    @Override
    public void register(Subscriber subscriber) {

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
