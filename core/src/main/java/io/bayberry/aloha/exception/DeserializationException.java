package io.bayberry.aloha.exception;

public class DeserializationException extends AlohaException {

    public DeserializationException(Object source, Class<?> targetType, Throwable cause) {
        super("Fail to deserialize object: " + source + ", targetType: " + targetType, cause);
    }
}
