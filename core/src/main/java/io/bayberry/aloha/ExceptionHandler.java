package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(String channel, Object value, EventBus eventBus, Exception exception) throws Exception;
}
