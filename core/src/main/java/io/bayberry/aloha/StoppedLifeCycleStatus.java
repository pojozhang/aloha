package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlreadyShutdownException;

public class StoppedLifeCycleStatus extends LifeCycleStatus {

    public StoppedLifeCycleStatus(LifeCycleContext context) {
        super(context);
    }

    @Override
    public void start() {
        throw new AlreadyShutdownException();
    }

    @Override
    public void shutdown() {
        throw new AlreadyShutdownException();
    }
}
