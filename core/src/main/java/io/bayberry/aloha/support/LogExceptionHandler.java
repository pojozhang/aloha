package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.ExceptionHandler;

public class LogExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(String channel, Object event, EventBus eventBus, Exception exception) {
        System.out.print(exception.getMessage());
    }
}
