package io.bayberry.aloha;

public class SubscribableMessage extends Message {

    public SubscribableMessage(Object payload) {
        super(payload);
    }

    public SubscribableMessage(Channel channel, Object payload) {
        super(channel, payload);
    }
}
