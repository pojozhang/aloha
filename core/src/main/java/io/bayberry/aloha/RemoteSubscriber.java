package io.bayberry.aloha;

import java.lang.reflect.Method;

public abstract class RemoteSubscriber<L extends RemoteListener> extends Subscriber<L> {

    protected RemoteSubscriber(String channel, Object target, Method method, ExceptionHandler exceptionHandler) {
        super(channel, target, method, exceptionHandler);
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
