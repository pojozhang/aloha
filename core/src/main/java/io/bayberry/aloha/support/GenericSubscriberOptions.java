package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.annotation.Subscribe;

public class GenericSubscriberOptions {

    public int threads;
    public Class<? extends ExceptionHandler> exceptionHandlerType;

    public GenericSubscriberOptions(Subscribe subscribe) {
        this.threads = subscribe.threads();
        this.exceptionHandlerType = subscribe.exceptionHandler();
    }

    private GenericSubscriberOptions() {
    }
}
