package io.bayberry.aloha.test;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.UnsupportedListenerException;
import io.bayberry.aloha.exception.UnsupportedMessageException;

public class TestMessageBus extends RemoteMessageBus<Object, byte[]> {

    protected TestMessageBus(String name) {
        super(name);
    }

    @Override
    protected Stream bindStream(Channel channel, Listener listener) throws UnsupportedListenerException {
        return null;
    }

    @Override
    public void post(Message message) throws UnsupportedMessageException {

    }
}
