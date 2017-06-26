package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Subscriber {

    String getChannel();

    Object getTarget();

    Method getMethod();

    Listener getListener();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void setListener(Listener listener);

    void accept(Object value) throws Exception;
}
