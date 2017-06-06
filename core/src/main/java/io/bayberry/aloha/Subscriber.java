package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class Subscriber {

    private final String channel;
    private final Object target;
    private final Method method;
    private Listener listener;

    protected Subscriber(final Object target, final Method method, final String channel) {
        this.target = target;
        this.method = method;
        this.channel = channel;
    }

    public final Object getTarget() {
        return target;
    }

    public final Method getMethod() {
        return method;
    }

    public final String getChannel() {
        return channel;
    }

    public final void setListener(Listener listener) {
        this.listener = listener;
    }

    public final Listener getListener() {
        return listener;
    }

    //TODO deserialize message and call doInvoke()
    public abstract void invoke(Object value) throws Exception;
}
