package io.bayberry.aloha;

import org.junit.Test;

import static org.junit.Assert.*;

public class SystemTest {

    @Test
    public void should_create_reuse_system_if_exists() {
        System first = System.create("name", SystemConfigFactory.load(this.getClass().getClassLoader().getResourceAsStream("aloha-test.yml")));
        System second = System.create("name", SystemConfigFactory.load(this.getClass().getClassLoader().getResourceAsStream("aloha-test.yml")));
        assertEquals(first, second);
    }
}