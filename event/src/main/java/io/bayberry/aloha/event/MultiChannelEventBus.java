package io.bayberry.aloha.event;

public interface MultiChannelEventBus extends EventBus {

    void post(String channel, Object event);

    String getChannel(Class eventType);
}
