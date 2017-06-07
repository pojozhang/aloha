package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.ExceptionHandler;

public class RethrowExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(String channel, Object event, EventBus eventBus, Exception exception) throws Exception {
        throw exception;
    }
}
