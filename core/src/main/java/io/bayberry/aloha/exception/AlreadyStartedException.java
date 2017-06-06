package io.bayberry.aloha.exception;

public class AlreadyStartedException extends LifeCycleException {

    public AlreadyStartedException() {
        super("Current object has already been started");
    }
}
