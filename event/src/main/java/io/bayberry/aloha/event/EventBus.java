package io.bayberry.aloha.event;

public interface EventBus {

    void register(EventSubscriber listener);

    void unregister(EventSubscriber listener);

    void post(Event<?> event);
}
