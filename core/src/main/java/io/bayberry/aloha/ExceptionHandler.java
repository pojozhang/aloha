package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(Channel channel, Object value, MessageBus messageBus, Exception exception) throws Exception;
}
