package io.bayberry.aloha;

import java.lang.reflect.Method;

public class Subscriber {

    protected String channel;
    protected Object target;
    protected Method method;
    protected ExceptionHandler exceptionHandler;
    protected Listener listener;

    protected Subscriber(String channel, Object target, Method method, ExceptionHandler exceptionHandler) {
        this.channel = channel;
        this.target = target;
        this.method = method;
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

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void accept(Object value) throws Exception {
        try {
            this.invoke(value);
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }

    protected void invoke(Object event) throws Exception {
        if (this.getMethod().getParameterTypes() != null) {
            this.getMethod().invoke(this.getTarget(), event);
        } else {
            this.getMethod().invoke(this.getTarget());
        }
    }
}
