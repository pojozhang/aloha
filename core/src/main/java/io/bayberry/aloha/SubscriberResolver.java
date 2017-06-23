package io.bayberry.aloha;

import java.util.List;

public interface SubscriberResolver {

    List<Subscriber> resolve(Object target, EventBus eventBus);
}
