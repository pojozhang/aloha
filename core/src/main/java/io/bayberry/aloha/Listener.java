package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Listener {

    Channel getChannel();

    Object getObject();

    Method getMethod();

    Stream getStream();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Stream stream, Object value) throws Exception;
}
