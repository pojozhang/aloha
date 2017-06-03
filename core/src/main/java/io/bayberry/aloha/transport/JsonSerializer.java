package io.bayberry.aloha.transport;

import com.alibaba.fastjson.JSON;

public class JsonSerializer<S> implements Serializer<String, S> {

    @Override
    public String serialize(S source) {
        return JSON.toJSONString(source);
    }
}
