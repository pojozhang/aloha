package io.bayberry.aloha.exception;

public class AlreadyShutdownException extends LifeCycleException {

    public AlreadyShutdownException() {
        super("Current object has already been shutdown");
    }
}
