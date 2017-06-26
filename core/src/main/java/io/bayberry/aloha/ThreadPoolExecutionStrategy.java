package io.bayberry.aloha;

public class ThreadPoolExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        runnable.run();
    }
}
