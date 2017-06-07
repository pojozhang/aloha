package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(Throwable throwable);
}
