package io.bayberry.aloha;

@FunctionalInterface
public interface ChannelResolver {

    Channel resolve(Class messageType);
}
