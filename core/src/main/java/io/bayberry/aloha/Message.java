package io.bayberry.aloha;

public abstract class Message<P> {

    protected Channel channel;
    protected P payload;

    public Channel getChannel() {
        return channel;
    }

    public P getPayload() {
        return payload;
    }
}
