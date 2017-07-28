package io.bayberry.aloha.util;

import io.bayberry.aloha.exception.AlohaException;

import java.util.Collection;

public class Assert {

    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) throwException(message);
    }

    public static void notNull(Object object, String message) {
        if (object == null) throwException(message);
    }

    private static void throwException(String message) {
        throw new AlohaException(message);
    }
}
