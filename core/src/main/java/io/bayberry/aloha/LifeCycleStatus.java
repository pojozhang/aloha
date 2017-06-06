package io.bayberry.aloha;

public abstract class LifeCycleStatus {

    protected final LifeCycleContext context;

    public LifeCycleStatus(final LifeCycleContext context) {
        this.context = context;
    }

    public abstract void start();

    public abstract void shutdown();
}
