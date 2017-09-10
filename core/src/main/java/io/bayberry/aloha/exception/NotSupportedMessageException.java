package io.bayberry.aloha.exception;

import io.bayberry.aloha.Message;

public class NotSupportedMessageException extends AlohaException {

    public NotSupportedMessageException(Class<? extends Message> messageType) {
        super("Not supported message type: " + messageType);
    }
}
