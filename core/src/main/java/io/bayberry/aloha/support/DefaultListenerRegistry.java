package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListenerRegistry implements ListenerRegistry {

    private final Map<Channel, List<Listener>> listeners = new ConcurrentHashMap<>();

    @Override
    public void register(List<Listener> listeners) {
        listeners.forEach(this::register);
    }

    @Override
    public void register(Listener listener) {
        Channel channel = listener.getChannel();
        List<Listener> listenersWithChannel = this.listeners.getOrDefault(channel, new ArrayList<>());
        listenersWithChannel.add(listener);
        this.listeners.put(channel, listenersWithChannel);
    }

    @Override
    public void unregister(List<Listener> listeners) {
        listeners.forEach(this::unregister);
    }

    @Override
    public void unregister(Listener listener) {
        this.listeners.values().forEach(listeners -> listeners.forEach(l -> {
            if (l.getTarget() == listener) {
                listeners.remove(l);
            }
        }));
    }

    @Override
    public Set<Channel> getChannels() {
        return this.listeners.keySet();
    }

    @Override
    public List<Listener> getListeners(Channel channel) {
        return this.listeners.get(channel);
    }
}
