package io.bayberry.aloha.exception;

import java.io.IOException;

public class UncheckedIOException extends UncheckedAlohaException {

    public UncheckedIOException(IOException cause) {
        super(cause);
    }
}
