package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;

public class SimpleChannelResolver implements ChannelResolver {

    @Override
    public Channel resolve(Class messageType) {
        return Channel.valueOf(messageType.getSimpleName());
    }
}
