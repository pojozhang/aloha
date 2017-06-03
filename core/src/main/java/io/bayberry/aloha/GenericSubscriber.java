package io.bayberry.aloha;

import io.bayberry.aloha.transport.Deserializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericSubscriber implements Subscriber {

    private final Object target;
    private final Method method;
    private final Deserializer deserializer;

    protected GenericSubscriber(final Object target, final Method method, final Deserializer deserializer) {
        this.target = target;
        this.method = method;
        this.deserializer = deserializer;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public void invoke(Object value) throws InvocationTargetException, IllegalAccessException {
        if (this.getMethod().getParameterTypes() != null) {
            Object parsedObject = this.deserializer.deserialize(value, this.getMethod().getParameterTypes()[0]);
            this.getMethod().invoke(this.getTarget(), parsedObject);
        } else {
            this.getMethod().invoke(this.getTarget());
        }
    }
}
