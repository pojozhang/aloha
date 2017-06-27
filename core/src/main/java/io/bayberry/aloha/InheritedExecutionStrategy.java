package io.bayberry.aloha;

public class InheritedExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        subscriber.getListener().getEventBus().getDefaultExecutionStrategy().execute(subscriber, runnable);
    }
}
