package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Subscriber {

    Channel getChannel();

    Object getTarget();

    Method getMethod();

    EventBus getEventBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Listener listener, Object value) throws Exception;
}
