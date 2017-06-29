package io.bayberry.aloha.support;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.AlohaException;

import java.lang.reflect.Method;
import java.util.List;

public class GenericSubscriber implements Subscriber {

    private List<String> channels;
    private Object target;
    private Method method;
    private EventBus eventBus;
    private ExceptionHandler exceptionHandler;
    private ExecutionStrategy executionStrategy;

    protected GenericSubscriber(List<String> channels, Object target, Method method, EventBus eventBus,
                                ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.channels = channels;
        this.target = target;
        this.method = method;
        this.eventBus = eventBus;
        this.exceptionHandler = exceptionHandler;
        this.executionStrategy = executionStrategy;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    @Override
    public ExecutionStrategy getExecutionStrategy() {
        return this.executionStrategy;
    }

    @Override
    public List<String> getChannels() {
        return channels;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void accept(Listener listener, Object value) throws Exception {
        try {
            this.invoke(listener, value);
        } catch (Exception exception) {
            this.handleException(exception, listener, value);
        }
    }

    protected void invoke(Listener listener, Object event) throws Exception {
        this.executionStrategy.execute(this, () -> {
            try {
                if (this.getMethod().getParameterTypes() != null) {
                    this.getMethod().invoke(this.getTarget(), event);
                } else {
                    this.getMethod().invoke(this.getTarget());
                }
            } catch (Exception e) {
                try {
                    this.handleException(e, listener, event);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected void handleException(Exception exception, Listener listener, Object value) throws Exception {
        try {
            if (this.getExceptionHandler() != null) {
                this.getExceptionHandler().handle(listener.getChannel(), value, listener.getEventBus(), exception);
            } else {
                listener.handleException(exception, value);
            }
        } catch (Exception e) {
            listener.handleException(e, value);
        }
    }
}
