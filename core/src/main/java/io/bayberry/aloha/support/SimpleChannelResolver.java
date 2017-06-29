package io.bayberry.aloha.support;

import io.bayberry.aloha.ChannelResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SimpleChannelResolver implements ChannelResolver {

    @Override
    public List<String> resolve(Class eventType) {
        return Arrays.asList(eventType.getSimpleName());
    }
}
