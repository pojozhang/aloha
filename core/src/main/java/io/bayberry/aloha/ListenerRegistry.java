package io.bayberry.aloha;

import java.util.List;
import java.util.Set;

public interface ListenerRegistry {

    void register(List<Listener> listeners);

    void register(Listener listener);

    void unregister(List<Listener> listeners);

    void unregister(Listener listener);

    Set<Channel> getChannels();

    List<Listener> getListeners(Channel channel);
}
