package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlohaException;

public class LifeCycleManager {

    private final LifeCycle lifeCycle;

    protected LifeCycleManager(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
        lifeCycle.onCreate();
    }

    public void start() {
        if (lifeCycle.isRunning()) {
            throw new AlohaException("This object has already been started");
        }
        this.isRunning = true;
        lifeCycle.onStart();
    }

    public void shutdown() {
        if (!isRunning()) {
            throw new AlohaException("This object has not been started yet");
        }
        this.isRunning = false;
        this.onDestroy();
    }
}
