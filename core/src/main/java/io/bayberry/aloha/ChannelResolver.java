package io.bayberry.aloha;

public interface ChannelResolver {

    Channel resolve(Class messageType);
}
