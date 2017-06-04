package io.bayberry.aloha;

public interface ChannelResolver {

    String resolve(Class eventType);
}
