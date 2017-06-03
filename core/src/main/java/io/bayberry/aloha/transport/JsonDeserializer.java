package io.bayberry.aloha.transport;

import com.alibaba.fastjson.JSON;

public class JsonDeserializer<T> implements Deserializer<T, String> {

    @Override
    public T deserialize(String source, Class<T> targetType) {
        return JSON.parseObject(source, targetType);
    }
}
