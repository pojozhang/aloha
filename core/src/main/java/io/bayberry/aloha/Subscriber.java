package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class Subscriber {

    private final String channel;
    private final Object target;
    private final Method method;
    private final ExceptionHandler exceptionHandler;
    private Listener listener;

    protected Subscriber(final Object target, final Method method, final String channel,
        final ExceptionHandler exceptionHandler) {
        this.target = target;
        this.method = method;
        this.channel = channel;
        this.exceptionHandler = exceptionHandler;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public String getChannel() {
        return channel;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler == null ? getListener().getEventBus().getDefaultExceptionHandler() : exceptionHandler;
    }

    public void respond(Object value) {
        try {
            this.invoke();
        } catch (Throwable throwable) {
            this.exceptionHandler.handle(throwable);
        }
    }

    protected abstract void invoke(Object value) throws Exception;
}
