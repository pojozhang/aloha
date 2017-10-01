package io.bayberry.aloha.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bayberry.aloha.exception.DeserializationException;
import io.bayberry.aloha.exception.SerializationException;
import io.bayberry.aloha.transport.Serializer;

import java.io.IOException;

public class JsonSerializer<S> implements Serializer<S, byte[]> {

    private static ObjectMapper JSON = new ObjectMapper();

    @Override
    public byte[] serialize(S source) {
        try {
            return JSON.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException(source, e);
        }
    }

    @Override
    public S deserialize(byte[] source, Class<S> targetType) {
        try {
            return JSON.readValue(source, targetType);
        } catch (IOException e) {
            throw new DeserializationException(source, targetType, e);
        }
    }
}
