package io.bayberry.aloha;

import java.util.List;

public interface MultiChannelEventBus extends EventBus {

    void post(String channel, Object event);

    String resolveChannel(Class eventType);

    AbstractMultiChannelEventListener getEventListener(String channel, List<Subscriber> subscribers);
}
