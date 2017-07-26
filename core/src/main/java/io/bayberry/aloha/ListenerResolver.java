package io.bayberry.aloha;

import java.util.List;

public interface ListenerResolver {

    List<Listener> resolve(Object target, MessageBus messageBus);
}
