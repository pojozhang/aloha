package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandlerFactory {

    ExceptionHandler provide(Class<? extends ExceptionHandler> exceptionHandlerType);
}
