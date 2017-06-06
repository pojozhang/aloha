package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlreadyStartedException;

public class RunningLifeCycleStatus extends LifeCycleStatus {

    public RunningLifeCycleStatus(LifeCycleContext context) {
        super(context);
    }

    @Override
    public void start() {
        throw new AlreadyStartedException();
    }

    @Override
    public void shutdown() {
        context.setStatus(context.STOPPED);
    }
}
