package io.bayberry.aloha.transport;

@FunctionalInterface
public interface Serializer<S, T> {

    T serialize(S source);
}
