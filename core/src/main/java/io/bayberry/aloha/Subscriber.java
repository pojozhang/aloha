package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class Subscriber {

    protected final String channel;
    protected final Object target;
    protected final Method method;
    protected final ExceptionHandler exceptionHandler;

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

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler == null ? getListener().getEventBus().getDefaultExceptionHandler() : exceptionHandler;
    }

    protected void handleException(Exception exception, Object value) throws Exception {
        if (this.getExceptionHandler() != null) {
            try {
                this.getExceptionHandler().handle(getChannel(), value, getListener().getEventBus(), exception);
            } catch (Exception e) {
                this.getListener().handleException(e, value);
            }
        }
    }

    public abstract Listener getListener();

    public abstract void accept(Object value) throws Exception;

    protected abstract void invoke(Object event) throws Exception;
}
