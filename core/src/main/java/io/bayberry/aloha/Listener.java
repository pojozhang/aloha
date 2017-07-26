package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Listener {

    Channel getChannel();

    Object getTarget();

    Method getMethod();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Receiver receiver, Object value) throws Exception;
}
