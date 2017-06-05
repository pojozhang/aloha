package io.bayberry.aloha.support;

import io.bayberry.aloha.ChannelResolver;

public class PrefixChannelResolverDecorator implements ChannelResolver {

    private final String prefix;
    private final ChannelResolver delegate;

    public PrefixChannelResolverDecorator(final String prefix, final ChannelResolver delegate) {
        this.prefix = prefix;
        this.delegate = delegate;
    }

    @Override
    public String resolve(Class eventType) {
        return this.prefix + this.delegate.resolve(eventType);
    }
}
