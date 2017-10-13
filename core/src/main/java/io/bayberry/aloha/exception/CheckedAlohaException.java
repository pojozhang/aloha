package io.bayberry.aloha.exception;

public class CheckedAlohaException extends Exception {

    public CheckedAlohaException(String message) {
        super(message);
    }

    public CheckedAlohaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedAlohaException(Throwable cause) {
        super(cause);
    }
}
