package io.bayberry.aloha.annotation;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.support.RethrowExceptionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    int threads() default 0;

    String channel() default "";

    Class<? extends ExceptionHandler> exceptionHandler() default RethrowExceptionHandler.class;
}
