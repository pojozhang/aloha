package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.ReceiverRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultReceiverRegistry implements ReceiverRegistry {

    private Map<Channel, Receiver> receivers = new ConcurrentHashMap<>();

    @Override
    public void register(Receiver receiver) {
        receivers.putIfAbsent(receiver.getChannel(), receiver);
    }

    @Override
    public void unregister(Receiver receiver) {
        receivers.remove(receiver.getChannel());
    }

    @Override
    public Collection<Receiver> getReceivers() {
        return receivers.values();
    }

    @Override
    public Receiver getReceiver(Channel channel) {
        return receivers.get(channel);
    }
}
