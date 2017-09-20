package io.bayberry.aloha.annotation;

import io.bayberry.aloha.*;
import io.bayberry.aloha.support.InheritedExceptionHandler;
import io.bayberry.aloha.support.InheritedExecutionStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Consume {

    String channel() default "";

    Class<? extends AnnotatedListenerResolver> resolver() default AnnotatedConsumerResolver.class;

    Class<? extends ExecutionStrategy> executionStrategy() default InheritedExecutionStrategy.class;

    Class<? extends ExceptionHandler> exceptionHandler() default InheritedExceptionHandler.class;
}
