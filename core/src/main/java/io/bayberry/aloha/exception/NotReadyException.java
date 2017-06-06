package io.bayberry.aloha.exception;

public class NotReadyException extends LifeCycleException {

    public NotReadyException() {
        super("Current object has not got ready yet");
    }
}
