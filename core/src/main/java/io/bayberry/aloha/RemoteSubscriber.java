package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class RemoteSubscriber extends Subscriber {

    private RemoteListener listener;

    protected RemoteSubscriber(Object target, Method method, String channel, ExceptionHandler exceptionHandler) {
        super(target, method, channel, exceptionHandler);
    }

    @Override
    public RemoteListener getListener() {
        return listener;
    }

    public void setListener(RemoteListener listener) {
        this.listener = listener;
    }

    @Override
    public void accept(Object value) throws Exception {
        try {
            this.invoke(this.getConvertedEventObject(value));
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }

    protected Object getConvertedEventObject(Object value) {
        return this.getListener().getEventBus().getDeserializer().deserialize(value, getMethod().getParameterTypes()[0]);
    }
}
