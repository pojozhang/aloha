package io.bayberry.aloha;

import java.util.Set;

public interface StreamRegistry {

    void register(Stream stream);

    void unregister(Stream stream);

    Set<Stream> getStreams(Channel channel);

    Set<Stream> getStreams();
}
