package io.bayberry.aloha.util;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.assertEquals;

public class ReflectionTest {

    @Test
    public void get_all_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(2, Reflection.getMethodsWithAnnotation(SimulationA.class, Annotation.class).size());
    }

    @Test
    public void get_empty_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(0, Reflection.getMethodsWithAnnotation(SimulationB.class, Annotation.class).size());
    }

    @Test
    public void get_first_level_interfaces_of_the_class() {
        assertEquals(1, Reflection.getAllInterfaces(SimulationA.class).size());
    }

    @Test
    public void get_all_interfaces_of_the_class() {
        assertEquals(2, Reflection.getAllInterfaces(SimulationB.class).size());
    }

    private static class SimulationA implements interfaceA {

        @Annotation
        public void method1() {
        }

        @Annotation
        public void method2() {
        }
    }

    private static class SimulationB implements interfaceB {

        public void method() {
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface Annotation {

    }

    private interface interfaceA {

    }

    private interface interfaceB extends interfaceA {

    }
}