package io.bayberry.aloha.util;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.Before;
import org.junit.Test;

public class ReflectionTest {

    private Reflection reflection;

    @Before
    public void setUp() {
        this.reflection = new Reflection();
    }

    @Test
    public void get_all_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(2, this.reflection.getMethodsWithAnnotation(SimulationA.class, Annotation.class).size());
    }

    @Test
    public void get_empty_methods_stream_of_the_class_with_the_annotation() {
        assertEquals(0, this.reflection.getMethodsWithAnnotation(SimulationB.class, Annotation.class).size());
    }

    @Test
    public void get_first_level_interfaces_of_the_class() {
        assertEquals(1, this.reflection.getAllInterfaces(SimulationA.class).size());
    }

    @Test
    public void get_all_interfaces_of_the_class() {
        assertEquals(2, this.reflection.getAllInterfaces(SimulationB.class).size());
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