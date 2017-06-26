package io.bayberry.aloha;

public interface ExecutionStrategy {

    void execute(Subscriber subscriber, Runnable runnable);
}
