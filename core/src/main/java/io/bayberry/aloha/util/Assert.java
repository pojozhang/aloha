package io.bayberry.aloha.util;

import io.bayberry.aloha.exception.AssertionError;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;

public class Assert {

    public static void notEmpty(Collection collection, String message, Object... args) {
        if (collection == null || collection.isEmpty()) throwException(message);
    }

    public static void notNull(Object object, String message, Object... args) {
        if (object == null) throwException(message, args);
    }

    public static <T> T notNull(Optional<T> optional, String message, Object... args) {
        if (!optional.isPresent()) throwException(message, args);
        return optional.get();
    }

    public static void isTrue(boolean condition, String message, Object... args) {
        if (!condition) throwException(message, args);
    }

    public static void isFalse(boolean condition, String message, Object... args) {
        if (condition) throwException(message, args);
    }

    private static void throwException(String message, Object... args) {
        if (args == null || args.length < 1)
            throw new AssertionError(message);
        throw new AssertionError(MessageFormat.format(message, args));
    }
}
