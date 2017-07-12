package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;

public class GenericChannelResolver implements ChannelResolver {

    @Override
    public Channel resolve(Class messageType) {
        return new Channel(messageType.getSimpleName());
    }
}
