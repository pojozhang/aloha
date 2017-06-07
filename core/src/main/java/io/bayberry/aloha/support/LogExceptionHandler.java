package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExceptionHandler implements ExceptionHandler {

    private Logger log = LoggerFactory.getLogger(LogExceptionHandler.class);

    @Override
    public void handle(String channel, Object event, EventBus eventBus, Exception exception) {
        log.error("An error occurs:", exception);
    }
}
