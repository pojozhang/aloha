package io.bayberry.aloha.transport;

@FunctionalInterface
public interface Deserializer<S, T> {

    T deserialize(S source, Class<T> targetType);
}
