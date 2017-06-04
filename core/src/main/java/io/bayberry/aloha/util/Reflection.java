package io.bayberry.aloha.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class Reflection {

    public Stream<Method> getMethodsWithAnnotation(Object target, Class<? extends Annotation> annotationType) {
        Method[] methods = target.getClass().getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationType));
    }
}
