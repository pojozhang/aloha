package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Hub {

    Channel getChannel();

    Object getContainer();

    Method getMethod();

    Receiver getReceiver();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Receiver receiver, Object value) throws Exception;
}
