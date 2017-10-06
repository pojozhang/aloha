package io.bayberry.aloha;

import java.lang.reflect.Method;
import java.util.Optional;

public interface Listener {

    Object getObject();

    Method getMethod();

    Optional<Class> getMessageType();

    Stream getStream();

    MessageBus getMessageBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Stream stream, Object value) throws Exception;
}
