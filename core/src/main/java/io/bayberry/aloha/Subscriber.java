package io.bayberry.aloha;

import java.lang.reflect.Method;
import java.util.List;

public interface Subscriber {

    List<String> getChannels();

    Object getTarget();

    Method getMethod();

    EventBus getEventBus();

    ExceptionHandler getExceptionHandler();

    ExecutionStrategy getExecutionStrategy();

    void accept(Listener listener, Object value) throws Exception;
}
