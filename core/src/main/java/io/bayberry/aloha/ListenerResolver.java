package io.bayberry.aloha;

import java.util.Set;

public interface ListenerResolver {

    Set<Listener> resolve(Object container, MessageBus messageBus);
}
