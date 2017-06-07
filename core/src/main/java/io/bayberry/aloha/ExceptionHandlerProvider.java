package io.bayberry.aloha;

@FunctionalInterface
public interface ExceptionHandlerProvider {

    ExceptionHandler provide(Class<? extends ExceptionHandler> exceptionHandlerType);
}
