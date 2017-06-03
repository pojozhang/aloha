package io.bayberry.aloha.transport;

@FunctionalInterface
public interface Serializer<T, S> {

    T serialize(S source);
}
