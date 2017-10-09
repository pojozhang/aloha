package io.bayberry.aloha.exception;

public class UncheckedAlohaException extends RuntimeException {

    public UncheckedAlohaException(String message) {
        super(message);
    }

    public UncheckedAlohaException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedAlohaException(Throwable cause) {
        super(cause);
    }
}
