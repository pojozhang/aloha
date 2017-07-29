package io.bayberry.aloha;

import java.util.List;

public interface ReceiverRegistry {

    void register(Receiver receiver);

    void unregister(Receiver receiver);

    List<Receiver> getReceiver(Channel channel);
}
