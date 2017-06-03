package io.bayberry.aloha;

import java.util.List;

public abstract class AbstractMultiChannelEventListener extends AbstractEventListener {

    protected final String channel;

    public AbstractMultiChannelEventListener(String channel, List<Subscriber> subscribers) {
        super(subscribers);
        this.channel = channel;
    }
}
