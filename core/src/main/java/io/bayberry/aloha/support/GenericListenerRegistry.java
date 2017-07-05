package io.bayberry.aloha.support;

import com.google.common.collect.Maps;
import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerRegistry;

import java.util.Collection;
import java.util.Map;

public class GenericListenerRegistry implements ListenerRegistry {

    private Map<Channel, Listener> channelListener = Maps.newConcurrentMap();

    @Override
    public void register(Listener listener) {
        channelListener.putIfAbsent(listener.getChannel(), listener);
    }

    @Override
    public void unregister(Listener listener) {
        channelListener.remove(listener.getChannel());
    }

    @Override
    public Collection<Listener> getListeners() {
        return channelListener.values();
    }

    @Override
    public Listener getListener(Channel channel) {
        return channelListener.get(channel);
    }
}
