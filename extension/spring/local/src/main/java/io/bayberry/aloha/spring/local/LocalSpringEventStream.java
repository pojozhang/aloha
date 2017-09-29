package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.LocalMessageBus;
import io.bayberry.aloha.LocalStream;

public class LocalSpringEventStream extends LocalStream {

    public LocalSpringEventStream(Channel channel, LocalMessageBus messageBus) {
        super(channel, messageBus);
        this.onCreate();
    }
}
