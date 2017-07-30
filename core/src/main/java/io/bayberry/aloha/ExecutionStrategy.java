package io.bayberry.aloha;

public interface ExecutionStrategy {

    void execute(Listener listener, Runnable runnable);
}
