package io.bayberry.aloha;

import io.bayberry.aloha.exception.AlohaException;

import java.lang.reflect.Method;
import java.util.Optional;

public abstract class AbstractListener implements Listener {

    protected Object container;
    protected Method method;
    protected MessageBus messageBus;
    protected ExceptionHandler exceptionHandler;
    protected ExecutionStrategy executionStrategy;
    protected Stream stream;

    @Override
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    @Override
    public ExecutionStrategy getExecutionStrategy() {
        return this.executionStrategy;
    }

    @Override
    public Object getObject() {
        return this.container;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Optional<Class> getMessageType() {
        Class<?>[] parameterTypes = this.method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length < 1) return Optional.empty();
        return Optional.of(parameterTypes[0]);
    }

    @Override
    public Stream getStream() {
        return stream;
    }

    @Override
    public MessageBus getMessageBus() {
        return messageBus;
    }

    public void setContainer(Object container) {
        this.container = container;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void setExecutionStrategy(ExecutionStrategy executionStrategy) {
        this.executionStrategy = executionStrategy;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    @Override
    public void accept(Stream stream, Object value) throws Exception {
        try {
            this.invoke(stream, value);
        } catch (Exception exception) {
            this.handleException(exception, stream, value);
        }
    }

    protected void invoke(Stream stream, Object message) throws Exception {
        this.executionStrategy.execute(this, () -> {
            try {
                if (this.getMethod().getParameterTypes() != null) {
                    this.getMethod().invoke(this.getObject(), message);
                } else {
                    this.getMethod().invoke(this.getObject());
                }
            } catch (Exception e) {
                try {
                    this.handleException(e, stream, message);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected void handleException(Exception exception, Stream stream, Object value) throws Exception {
        try {
            if (this.getExceptionHandler() != null) {
                this.getExceptionHandler().handle(stream.getChannel(), value, stream.getMessageBus(), exception);
            } else {
                stream.handleException(exception, value);
            }
        } catch (Exception e) {
            stream.handleException(e, value);
        }
    }
}
