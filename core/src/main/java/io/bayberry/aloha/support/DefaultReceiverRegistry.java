package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.ReceiverRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultReceiverRegistry implements ReceiverRegistry {

    private Map<Channel, Set<Receiver>> receivers = new ConcurrentHashMap<>();

    @Override
    public void register(Receiver receiver) {
        Set<Receiver> receiverSet = this.receivers.getOrDefault(receiver.getChannel(), new HashSet<>());
        receiverSet.add(receiver);
        receivers.putIfAbsent(receiver.getChannel(), receiverSet);
    }

    @Override
    public void unregister(Receiver receiver) {
        Set<Receiver> receiverList = this.receivers.get(receiver.getChannel());
        if (receiverList != null) {
            receiverList.remove(receiver);
            if (receiverList.isEmpty()) {
                this.receivers.remove(receiver.getChannel());
            }
        }
    }

    @Override
    public Set<Receiver> getReceivers(Channel channel) {
        return receivers.getOrDefault(channel, Collections.EMPTY_SET);
    }

    @Override
    public Set<Receiver> getReceivers() {
        Set<Receiver> allReceivers = new HashSet<>();
        this.receivers.values().forEach(allReceivers::addAll);
        return allReceivers;
    }
}

