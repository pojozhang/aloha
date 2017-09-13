package io.bayberry.aloha;

public class ConsumableMessage extends Message {

    public ConsumableMessage(Object payload) {
        super(payload);
    }

    public ConsumableMessage(Channel channel, Object payload) {
        super(channel, payload);
    }
}
