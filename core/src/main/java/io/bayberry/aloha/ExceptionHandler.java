package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(Channel channel, Object value, EventBus eventBus, Exception exception) throws Exception;
}
