package io.bayberry.aloha.event;

public interface MultiChannelEventBus extends EventBus {

    void post(String channel, Object event);
}
