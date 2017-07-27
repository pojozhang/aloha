package io.bayberry.aloha.support;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.transport.Deserializer;

public class JsonDeserializer<T> implements Deserializer<T, String> {

    @Override
    public T deserialize(String source, Class<T> targetType) {
        return JSON.parseObject(source, targetType);
    }
}