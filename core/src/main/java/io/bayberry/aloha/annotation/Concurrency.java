package io.bayberry.aloha.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Concurrency {

    int minCount() default 1;

    int maxCount() default 1;

    int capacity() default 1;

    long keepAliveTime() default 30000;

    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
