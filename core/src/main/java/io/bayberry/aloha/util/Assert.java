package io.bayberry.aloha.util;

import io.bayberry.aloha.exception.AlohaException;

import java.util.Collection;
import java.util.Optional;

public class Assert {

    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) throwException(message);
    }

    public static void notNull(Object object, String message) {
        if (object == null) throwException(message);
    }

    public static <T> T notNull(Optional<T> optional, String message) {
        if (!optional.isPresent()) throwException(message);
        return optional.get();
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) throwException(message);
    }

    public static void isFalse(boolean condition, String message) {
        if (condition) throwException(message);
    }

    private static void throwException(String message) {
        throw new AlohaException(message);
    }
}
