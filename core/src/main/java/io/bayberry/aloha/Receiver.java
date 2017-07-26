package io.bayberry.aloha;

import java.util.List;

public interface Receiver extends LifeCycle {

    void notifyAll(Object source);

    void register(List<Listener> listeners);

    void register(Listener listener);

    void unregister(List<Listener> listeners);

    void unregister(Listener listener);

    Channel getChannel();

    List<Listener> getListeners();

    MessageBus getMessageBus();

    void handleException(Exception exception, Object value) throws Exception;
}
