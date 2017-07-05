package io.bayberry.aloha.util;

import static java.util.stream.Collectors.toSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Reflection {

    public Set<Method> getMethodsWithAnnotation(Class targetClass, Class<? extends Annotation> annotationType) {
        Method[] methods = targetClass.getMethods();
        return Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(annotationType))
            .collect(toSet());
    }

    public Set<Class> getAllSuperClasses(Class targetClass) {
        Set<Class> superClasses = new HashSet<>();
        Class superClass = targetClass.getSuperclass();
        while (superClass != null) {
            superClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }
        return superClasses;
    }

    public Set<Class> getAllInterfaces(Class targetClass) {
        Set<Class> interfaces = new HashSet<>();
        for (Class aInterface : targetClass.getInterfaces()) {
            interfaces.add(aInterface);
            interfaces.addAll(this.getAllInterfaces(aInterface));
        }
        return interfaces;
    }
}
