package io.bayberry.aloha;

import java.util.Set;

public interface ReceiverRegistry {

    void register(Receiver receiver);

    void unregister(Receiver receiver);

    Set<Receiver> getReceivers(Channel channel);

    Set<Receiver> getReceivers();
}
