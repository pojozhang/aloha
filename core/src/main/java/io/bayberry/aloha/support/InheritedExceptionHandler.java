package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.ExceptionHandler;

public class InheritedExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(String channel, Object event, EventBus eventBus, Exception exception) throws Exception {
        eventBus.getDefaultExceptionHandler().handle(channel, event, eventBus, exception);
    }
}
