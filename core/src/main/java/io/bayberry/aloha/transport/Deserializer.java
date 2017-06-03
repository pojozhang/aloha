package io.bayberry.aloha.transport;

@FunctionalInterface
public interface Deserializer<T, S> {

    T deserialize(S source, Class<T> targetType);
}
