package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.ReceiverRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultReceiverRegistry implements ReceiverRegistry {

    private Map<Channel, List<Receiver>> receivers = new ConcurrentHashMap<>();

    @Override
    public void register(Receiver receiver) {
        List<Receiver> receiverList = this.receivers.getOrDefault(receiver.getChannel(), new ArrayList<>());
        receiverList.add(receiver);
        receivers.putIfAbsent(receiver.getChannel(), receiverList);
    }

    @Override
    public void unregister(Receiver receiver) {
        List<Receiver> receiverList = this.receivers.get(receiver.getChannel());
        if (receiverList != null) {
            receiverList.remove(receiver);
            if (receiverList.size() == 0) {
                this.receivers.remove(receiver.getChannel());
            }
        }
    }

    @Override
    public List<Receiver> getReceiver(Channel channel) {
        return receivers.getOrDefault(channel, Collections.EMPTY_LIST);
    }
}

