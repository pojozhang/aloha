package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Subscriber;

public class InheritedExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        subscriber.getListener().getEventBus().getDefaultExecutionStrategy().execute(subscriber, runnable);
    }
}
