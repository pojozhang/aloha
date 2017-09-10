package io.bayberry.aloha.exception;

import io.bayberry.aloha.Message;

public class NotSupportedMessageException extends AlohaException {

    public NotSupportedMessageException(Message message) {
        super("Not supported message type: " + message.getClass());
    }
}
