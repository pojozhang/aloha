package io.bayberry.aloha;

public abstract class LifeCycleStatus implements LifeCycle {

    protected final LifeCycleContext context;

    public LifeCycleStatus(final LifeCycleContext context) {
        this.context = context;
    }
}
