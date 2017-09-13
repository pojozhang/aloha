package io.bayberry.aloha;

public abstract class Message<P> {

    protected Channel channel;
    protected P payload;

    public Message(P payload) {
        this(null, payload);
    }

    public Message(Channel channel, P payload) {
        this.channel = channel;
        this.payload = payload;
    }

    public Channel getChannel() {
        return channel;
    }

    public P getPayload() {
        return payload;
    }
}
