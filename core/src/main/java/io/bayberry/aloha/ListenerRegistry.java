package io.bayberry.aloha;

import java.util.Collection;

public interface ListenerRegistry {

    void register(Listener listener);

    void unregister(Listener listener);

    Collection<Listener> getListeners();

    Listener getListener(Channel channel);
}
