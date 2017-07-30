package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListenerRegistry implements ListenerRegistry {

    private final Map<Channel, Set<Listener>> listeners = new ConcurrentHashMap<>();

    @Override
    public void register(Set<Listener> listeners) {
        listeners.forEach(this::register);
    }

    @Override
    public void register(Listener listener) {
        Channel channel = listener.getChannel();
        Set<Listener> listenersWithChannel = this.listeners.getOrDefault(channel, new HashSet<>());
        listenersWithChannel.add(listener);
        this.listeners.put(channel, listenersWithChannel);
    }

    @Override
    public void unregister(Set<Listener> listeners) {
        listeners.forEach(this::unregister);
    }

    @Override
    public void unregister(Listener listener) {
        this.listeners.values().forEach(listeners -> listeners.forEach(l -> {
            if (l.getContainer() == listener) {
                listeners.remove(l);
            }
        }));
    }

    @Override
    public Set<Channel> getChannels() {
        return this.listeners.keySet();
    }

    @Override
    public Set<Listener> getListeners(Channel channel) {
        return this.listeners.get(channel);
    }

    @Override
    public Set<Listener> getListeners() {
        Set<Listener> allListeners = new HashSet<>();
        this.listeners.values().forEach(allListeners::addAll);
        return allListeners;
    }
}
