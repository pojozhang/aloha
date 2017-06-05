package io.bayberry.aloha;

import java.util.List;
import java.util.Set;

public interface SubscriberRegistry {

    void register(List<Subscriber> subscribers);

    void register(Subscriber subscriber);

    void unregister(List<Subscriber> subscribers);

    void unregister(Subscriber subscriber);

    Set<String> getChannels();

    List<Subscriber> getSubscribers(String channel);
}
