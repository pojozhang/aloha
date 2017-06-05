package io.bayberry.aloha.support;

import io.bayberry.aloha.ChannelResolver;

public class GenericChannelResolver implements ChannelResolver {

    @Override
    public String resolve(Class eventType) {
        return eventType.getSimpleName();
    }
}
