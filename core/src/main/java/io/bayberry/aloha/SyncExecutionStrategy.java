package io.bayberry.aloha;

public class SyncExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        runnable.run();
    }
}
