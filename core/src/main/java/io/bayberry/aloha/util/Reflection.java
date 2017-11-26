package io.bayberry.aloha.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.stream.Collectors.toSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class Reflection {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static Set<Method> getMethodsWithAnnotation(Class targetClass, Class<? extends Annotation> annotationType) {
        Method[] methods = targetClass.getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationType))
                .collect(toSet());
    }

    public static Set<Class> getAllSuperClasses(Class targetClass) {
        Set<Class> superClasses = new HashSet<>();
        Class superClass = targetClass.getSuperclass();
        while (superClass != null) {
            superClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }
        return superClasses;
    }

    public static Set<Class> getAllInterfaces(Class targetClass) {
        Set<Class> interfaces = new HashSet<>();
        for (Class aInterface : targetClass.getInterfaces()) {
            interfaces.add(aInterface);
            interfaces.addAll(getAllInterfaces(aInterface));
        }
        return interfaces;
    }

    public static <T> T convertMapToObject(Map map, Class<T> type) {
        return OBJECT_MAPPER.convertValue(map, type);
    }
}
