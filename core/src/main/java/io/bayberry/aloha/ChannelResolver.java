package io.bayberry.aloha;

import java.util.List;

public interface ChannelResolver {

    List<String> resolve(Class eventType);
}
