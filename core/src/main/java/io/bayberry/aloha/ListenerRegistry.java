package io.bayberry.aloha;

import java.util.Set;

public interface ListenerRegistry {

    void register(Set<Listener> listeners);

    void register(Listener listener);

    void unregister(Set<Listener> listeners);

    void unregister(Listener listener);

    Set<Channel> getChannels();

    Set<Listener> getListeners(Channel channel);

    Set<Listener> getListeners();
}
