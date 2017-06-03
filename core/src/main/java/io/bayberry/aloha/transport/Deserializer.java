package io.bayberry.aloha.transport;

public interface Deserializer<T, S> {

    T deserialize(S source, Class<T> targetType);
}
