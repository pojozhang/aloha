package io.bayberry.aloha.support;

import com.google.common.collect.Maps;
import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ListenerRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericListenerRegistry implements ListenerRegistry {

    private Map<Channel, Listener> channelListener = Maps.newConcurrentMap();

    @Override
    public void register(Listener listener) {
        channelListener.put(listener.getChannel(), listener);
    }

    @Override
    public void unregister(Listener listener) {
        channelListener.remove(listener.getChannel());
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>(channelListener.values());
    }

    @Override
    public Listener getListener(Channel channel) {
        return channelListener.get(channel);
    }
}
