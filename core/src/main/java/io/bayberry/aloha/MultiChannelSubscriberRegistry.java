package io.bayberry.aloha;

import java.util.List;
import java.util.Set;

public interface MultiChannelSubscriberRegistry extends SubscriberRegistry {

    Set<String> getChannels();

    List<SubscriberInvocation> getInvocations(String channel);
}
