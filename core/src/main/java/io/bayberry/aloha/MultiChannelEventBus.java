package io.bayberry.aloha;

public interface MultiChannelEventBus extends EventBus {

    void post(String channel, Object event);

    String resolveChannel(Class eventType);
}
