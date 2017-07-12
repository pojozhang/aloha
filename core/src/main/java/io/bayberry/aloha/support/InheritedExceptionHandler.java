package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.ExceptionHandler;

public class InheritedExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(Channel channel, Object value, MessageBus messageBus, Exception exception) throws Exception {
        messageBus.getDefaultExceptionHandler().handle(channel, value, messageBus, exception);
    }
}
