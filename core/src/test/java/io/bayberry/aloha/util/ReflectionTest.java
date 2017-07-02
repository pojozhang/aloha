package io.bayberry.aloha.util;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.*;

public class ReflectionTest {

    private Reflection reflection;

    @Before
    public void setUp() {
        this.reflection = new Reflection();
    }

    @Test
    public void get_all_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(2, this.reflection.getMethodsWithAnnotation(SimulationA.class, Annotation.class).count());
    }

    @Test
    public void get_empty_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(0, this.reflection.getMethodsWithAnnotation(SimulationB.class, Annotation.class).count());
    }

    private static class SimulationA {

        @Annotation
        public void method1() {
        }

        @Annotation
        public void method2() {
        }
    }

    private static class SimulationB {

        public void method() {
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface Annotation {

    }
}