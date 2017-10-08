package io.bayberry.aloha.spring.redis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisListeners {

    String stream() default "";
}