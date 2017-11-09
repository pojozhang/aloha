package io.bayberry.aloha.config;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class YamlPropertySourceReaderTest {

    private PropertySourceReader reader;

    @Before
    public void setUp() {
        reader = new YamlPropertySourceReader();
    }

    @Test
    public void should_read_property_source_from_yaml_file_properly() {
        PropertySource propertySource = reader.read(this.getClass().getClassLoader().getResourceAsStream("aloha-test.yml"));
        assertTrue(propertySource.get("aloha") instanceof Map);
    }
}