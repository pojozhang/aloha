package io.bayberry.aloha.exception;

public class AlohaException extends RuntimeException {

    public AlohaException(String message) {
        super(message);
    }

    public AlohaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlohaException(Throwable cause) {
        super(cause);
    }
}
