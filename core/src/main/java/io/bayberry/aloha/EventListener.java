package io.bayberry.aloha;

public interface EventListener {

    void start();

    void notifyAll(Object value);
}
