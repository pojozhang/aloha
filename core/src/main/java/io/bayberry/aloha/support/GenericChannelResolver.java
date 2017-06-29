package io.bayberry.aloha.support;

import io.bayberry.aloha.ChannelResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GenericChannelResolver implements ChannelResolver {

    @Override
    public List<String> resolve(Class eventType) {
        List<String> channels = new ArrayList<>();
        channels.add(eventType.getSimpleName());
        channels.addAll(Arrays.stream(eventType.getInterfaces()).map(Class::getSimpleName).collect(toList()));
        if (!Object.class.equals(eventType)) {
            channels.addAll(this.resolve(eventType.getSuperclass()));
        }
        return channels;
    }
}
