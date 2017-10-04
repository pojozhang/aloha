package io.bayberry.aloha.support.serializer;

public interface Serializer<S, T> {

    T serialize(S source);

    S deserialize(T source, Class<S> targetType);
}
