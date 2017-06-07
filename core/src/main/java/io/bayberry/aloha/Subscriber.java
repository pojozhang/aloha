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

    public void accept(Object value) throws Exception {
        try {
            this.invoke(getConvertedEventObject(value));
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }

    protected Object getConvertedEventObject(Object value) {
        return this.getListener().getEventBus().getDeserializer().deserialize(value, getMethod().getParameterTypes()[0]);
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

    protected abstract void invoke(Object event) throws Exception;
}
