package io.bayberry.aloha.support;

import com.google.common.collect.Maps;
import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.ReceiverRegistry;

import java.util.Collection;
import java.util.Map;

public class DefaultReceiverRegistry implements ReceiverRegistry {

    private Map<Channel, Receiver> channelListener = Maps.newConcurrentMap();

    @Override
    public void register(Receiver receiver) {
        channelListener.putIfAbsent(receiver.getChannel(), receiver);
    }

    @Override
    public void unregister(Receiver receiver) {
        channelListener.remove(receiver.getChannel());
    }

    @Override
    public Collection<Receiver> getReceivers() {
        return channelListener.values();
    }

    @Override
    public Receiver getReceiver(Channel channel) {
        return channelListener.get(channel);
    }
}
