package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class LocalSubscriber extends Subscriber {

    private LocalListener listener;

    protected LocalSubscriber(Object target, Method method, String channel, ExceptionHandler exceptionHandler) {
        super(target, method, channel, exceptionHandler);
    }

    @Override
    public void accept(Object value) throws Exception {
        try {
            this.invoke(value);
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }

    @Override
    public LocalListener getListener() {
        return listener;
    }

    public void setListener(LocalListener listener) {
        this.listener = listener;
    }
}
