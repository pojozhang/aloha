package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogExceptionHandler implements ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultLogExceptionHandler.class);

    @Override
    public void handle(Channel channel, Object value, MessageBus messageBus, Exception exception) {
        log.error("An error occurs:", exception);
    }
}
