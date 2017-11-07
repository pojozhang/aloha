package io.bayberry.aloha.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlPropertySourceReader implements PropertySourceReader {

    @Override
    public PropertySource read(InputStream inputStream) {
        Map<String, Object> map = new Yaml().load(new UnicodeReader(inputStream));
        Map<String, Object> flattenedMap = this.buildFlattenedMap(map);
        return new MapPropertySource(flattenedMap);
    }

    private Map<String, Object> buildFlattenedMap(Map<String, Object> map) {
        Map<String, Object> flattenedMap = new LinkedHashMap<>();
        map.forEach((key, value) -> flattenedMap.putAll(this.buildFlattenedMap(key, value)));
        return flattenedMap;
    }

    private Map<String, Object> buildFlattenedMap(String property, Object object) {
        Map<String, Object> flattenedMap = new LinkedHashMap<>();
        flattenedMap.put(property, object);
        if (object instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) object;
            map.forEach((key, value) -> flattenedMap.putAll(this.buildFlattenedMap(property + "." + key, value)));
        }
        return flattenedMap;
    }
}
