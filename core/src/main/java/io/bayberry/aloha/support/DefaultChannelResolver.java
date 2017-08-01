package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;

import java.lang.reflect.Method;

public class DefaultChannelResolver implements ChannelResolver {

    @Override
    public Channel resolve(Class messageType) {
        return new Channel(messageType.getSimpleName());
    }
}
