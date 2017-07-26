package io.bayberry.aloha;

public class ReadyLifeCycleStatus extends LifeCycleStatus {

    public ReadyLifeCycleStatus(LifeCycleContext context) {
        super(context);
    }

    @Override
    public void start() {
        context.setStatus(context.RUNNING);
    }

    @Override
    public void stop() {
        context.setStatus(context.STOPPED);
    }
}
