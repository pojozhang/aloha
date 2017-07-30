package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Listener;

public class InheritedExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Listener listener, Runnable runnable) {
        listener.getMessageBus().getDefaultExecutionStrategy().execute(listener, runnable);
    }
}
