package io.bayberry.aloha.support;

import io.bayberry.aloha.ChannelResolver;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PrefixChannelResolverDecorator implements ChannelResolver {

    private final String prefix;
    private final ChannelResolver delegate;

    public PrefixChannelResolverDecorator(final String prefix, final ChannelResolver delegate) {
        this.prefix = prefix;
        this.delegate = delegate;
    }

    @Override
    public List<String> resolve(Class eventType) {
        return this.delegate.resolve(eventType).stream().map(channel -> this.prefix + channel).collect(toList());
    }
}
