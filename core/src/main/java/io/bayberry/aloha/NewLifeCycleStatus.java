package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlreadyShutdownException;
import io.bayberry.aloha.exception.NotReadyException;

public class NewLifeCycleStatus extends LifeCycleStatus {

    public NewLifeCycleStatus(LifeCycleContext context) {
        super(context);
    }

    @Override
    public void start() {
        throw new NotReadyException();
    }

    @Override
    public void shutdown() {
        throw new AlreadyShutdownException();
    }
}
