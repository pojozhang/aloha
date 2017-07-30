package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Listener {

    Channel getChannel();

    Object getContainer();

    Method getMethod();

    Receiver getReceiver();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Receiver receiver, Object value) throws Exception;
}
