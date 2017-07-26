package io.bayberry.aloha;

import java.util.Collection;

public interface ReceiverRegistry {

    void register(Receiver receiver);

    void unregister(Receiver receiver);

    Collection<Receiver> getReceivers();

    Receiver getReceiver(Channel channel);
}
