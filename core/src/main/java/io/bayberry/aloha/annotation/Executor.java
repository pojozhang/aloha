package io.bayberry.aloha.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Executor {

    int minCount() default 1;

    int maxCount() default 1;

    int queueSize() default 0;

    long keepAliveTime() default 30000;

    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
