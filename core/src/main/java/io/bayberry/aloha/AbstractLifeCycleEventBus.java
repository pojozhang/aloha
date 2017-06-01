package io.bayberry.aloha;

public abstract class AbstractLifeCycleEventBus implements EventBus, LifeCycle {

    protected AbstractLifeCycleEventBus() {
        this.onCreate();
    }

    @Override
    public final void start() {
        this.onStart();
    }

    @Override
    public final void shutdown() {
        this.onDestroy();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
    }
}
