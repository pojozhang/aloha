package io.bayberry.aloha.transport;

public interface Serializer<T, S> {

    T serialize(S source);
}
