package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;

public class GenericChannelResolver implements ChannelResolver {

    @Override
    public Channel resolve(Class eventType) {
        return new Channel(eventType.getSimpleName());
    }
}
