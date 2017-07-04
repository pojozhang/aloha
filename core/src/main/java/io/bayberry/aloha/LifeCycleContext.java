package io.bayberry.aloha;

public abstract class LifeCycleContext implements LifeCycle {

    public final LifeCycleStatus NEW = new NewLifeCycleStatus(this);
    public final LifeCycleStatus READY = new ReadyLifeCycleStatus(this);
    public final LifeCycleStatus RUNNING = new RunningLifeCycleStatus(this);
    public final LifeCycleStatus STOPPED = new StoppedLifeCycleStatus(this);
    private LifeCycleStatus status = NEW;
    private boolean isInitialized = false;

    public LifeCycleContext() {
        this.status = READY;
    }

    @Override
    public void start() {
        if (!isInitialized) {
            isInitialized = true;
        }
        this.status.start();
        this.onStart();
    }

    @Override
    public void shutdown() {
        this.status.shutdown();
        this.onDestroy();
    }

    protected abstract void onCreate();

    protected abstract void onStart();

    protected abstract void onDestroy();

    public LifeCycleStatus getStatus() {
        return status;
    }

    public void setStatus(LifeCycleStatus status) {
        this.status = status;
    }
}
