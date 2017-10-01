package io.bayberry.aloha.exception;

public class SerializationException extends AlohaException {

    public SerializationException(Object source, Throwable cause) {
        super("Fail to serialize object: " + source, cause);
    }
}
