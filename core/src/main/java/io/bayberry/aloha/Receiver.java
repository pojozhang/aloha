package io.bayberry.aloha;

import java.util.List;

public interface Receiver extends LifeCycle {

    void notifyAll(Object source);

    void register(List<Subscriber> subscribers);

    void register(Subscriber subscriber);

    void unregister(List<Subscriber> subscribers);

    void unregister(Subscriber subscriber);

    Channel getChannel();

    List<Subscriber> getSubscribers();

    MessageBus getMessageBus();

    void handleException(Exception exception, Object value) throws Exception;
}
