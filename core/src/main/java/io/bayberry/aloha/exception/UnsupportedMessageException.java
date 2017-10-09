package io.bayberry.aloha.exception;

import io.bayberry.aloha.Message;

public class UnsupportedMessageException extends UncheckedAlohaException {

    public UnsupportedMessageException(Message message) {
        super("Not supported message type: " + message.getClass());
    }
}
