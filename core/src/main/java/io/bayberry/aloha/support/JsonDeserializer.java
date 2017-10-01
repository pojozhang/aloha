package io.bayberry.aloha.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bayberry.aloha.exception.DeserializationException;
import io.bayberry.aloha.transport.Deserializer;

import java.io.IOException;

public class JsonDeserializer<T> implements Deserializer<byte[], T> {

    private static ObjectMapper JSON = new ObjectMapper();

    @Override
    public T deserialize(byte[] source, Class<T> targetType) {
        try {
            return JSON.readValue(source, targetType);
        } catch (IOException e) {
            throw new DeserializationException(source, targetType, e);
        }
    }
}
