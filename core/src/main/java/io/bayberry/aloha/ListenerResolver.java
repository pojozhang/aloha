package io.bayberry.aloha;

import java.util.List;

public interface ListenerResolver {

    List<Listener> resolve(Object container, MessageBus messageBus);
}
