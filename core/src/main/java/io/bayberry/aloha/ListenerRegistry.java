package io.bayberry.aloha;

import java.util.List;

public interface ListenerRegistry {

    void register(Listener listener);

    void unregister(Listener listener);

    List<Listener> getListeners();

    Listener getListener(Channel channel);
}
