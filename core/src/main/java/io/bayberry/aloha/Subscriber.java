package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Subscriber {

    Channel getChannel();

    Object getTarget();

    Method getMethod();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Receiver listener, Object value) throws Exception;
}
