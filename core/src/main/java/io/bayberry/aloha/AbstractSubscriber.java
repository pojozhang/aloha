package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class AbstractSubscriber implements Subscriber {

    private Object target;
    private Method method;

    protected AbstractSubscriber(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }
}
