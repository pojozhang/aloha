package io.bayberry.aloha;

public abstract class MessageBusContext extends LifeCycleContext {

    private final MessageBus messageBus;

    public MessageBusContext(final MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    public MessageBus getMessageBus() {
        return messageBus;
    }
}
