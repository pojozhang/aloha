package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.ExceptionHandlerFactory;
import io.bayberry.aloha.exception.AlohaException;

public class DefaultExceptionHandlerFactory implements ExceptionHandlerFactory {

    @Override
    public ExceptionHandler provide(Class<? extends ExceptionHandler> exceptionHandlerType) {
        try {
            return exceptionHandlerType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AlohaException(e);
        }
    }
}
