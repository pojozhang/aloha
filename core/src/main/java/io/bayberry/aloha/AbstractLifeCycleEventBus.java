package io.bayberry.aloha;

public abstract class AbstractLifeCycleEventBus implements EventBus, LifeCycle {

    protected AbstractLifeCycleEventBus() {
        this.onCreate();
    }

    @Override
    public final void shutdown() {
        this.onDestroy();
    }
}
