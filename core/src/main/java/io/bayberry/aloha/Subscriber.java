package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class Subscriber {

    private final String channel;
    private final Object target;
    private final Method method;

    protected Subscriber(final Object target, final Method method, String channel) {
        this.target = target;
        this.method = method;
        this.channel = channel;
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

    public abstract void invoke(Object value) throws Exception;
}
