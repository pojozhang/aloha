package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class LocalSubscriber<L extends LocalListener> extends Subscriber<L> {

    protected LocalSubscriber(String channel, Object target, Method method, ExceptionHandler exceptionHandler) {
        super(channel, target, method, exceptionHandler);
    }

    @Override
    public void accept(Object value) throws Exception {
        try {
            this.invoke(value);
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }
}
