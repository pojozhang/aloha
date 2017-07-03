package io.bayberry.aloha.util;

import static java.util.stream.Collectors.toSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class Reflection {

    public Set<Method> getMethodsWithAnnotation(Class targetClass, Class<? extends Annotation> annotationType) {
        Method[] methods = targetClass.getMethods();
        return Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(annotationType))
            .collect(toSet());
    }

    public Set<Class> getAllInterfaces(Class targetClass) {
        Set<Class> interfaces = Arrays.stream(targetClass.getInterfaces()).collect(toSet());
        interfaces.forEach(i -> interfaces.addAll(this.getAllInterfaces(i)));
        return interfaces;
    }
}
