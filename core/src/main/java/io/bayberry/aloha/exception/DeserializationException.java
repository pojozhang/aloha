package io.bayberry.aloha.exception;

public class DeserializationException extends UncheckedAlohaException {

    public DeserializationException(Object source, Class<?> targetType, Throwable cause) {
        super("Fail to deserialize object: " + source + ", targetType: " + targetType, cause);
    }

    public DeserializationException(Object source, String targetType, Throwable cause) {
        super("Fail to deserialize object: " + source + ", targetType: " + targetType, cause);
    }
}
