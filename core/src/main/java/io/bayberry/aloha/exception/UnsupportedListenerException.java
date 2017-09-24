package io.bayberry.aloha.exception;

import io.bayberry.aloha.Listener;

public class UnsupportedListenerException extends AlohaException {

    public UnsupportedListenerException(Listener listener) {
        super("Not supported listener type: " + listener.getClass());
    }
}
