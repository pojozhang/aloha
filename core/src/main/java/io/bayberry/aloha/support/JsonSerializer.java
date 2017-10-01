package io.bayberry.aloha.support;

import com.alibaba.fastjson.JSON;
import io.bayberry.aloha.transport.Serializer;

public class JsonSerializer<S> implements Serializer<S, String> {

    @Override
    public String serialize(S source) {
        return JSON.toJSONString(source);
    }
}
