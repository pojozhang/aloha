package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.ExceptionHandlerProvider;
import io.bayberry.aloha.exception.AlohaException;

public class GenericExceptionHandlerProvider implements ExceptionHandlerProvider {

    @Override
    public ExceptionHandler provide(Class<? extends ExceptionHandler> exceptionHandlerType) {
        try {
            return exceptionHandlerType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AlohaException(e);
        }
    }
}
