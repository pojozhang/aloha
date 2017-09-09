package io.bayberry.aloha;

public abstract class ChannelMessage implements Message {

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
