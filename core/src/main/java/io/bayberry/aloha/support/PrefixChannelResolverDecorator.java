package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.ChannelResolver;

public class PrefixChannelResolverDecorator implements ChannelResolver {

    private final String prefix;
    private final ChannelResolver delegate;

    public PrefixChannelResolverDecorator(final String prefix, final ChannelResolver delegate) {
        this.prefix = prefix;
        this.delegate = delegate;
    }

    @Override
    public Channel resolve(Class messageType) {
        return new Channel(this.prefix + this.delegate.resolve(messageType).getName());
    }
}
