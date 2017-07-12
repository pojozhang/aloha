package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.exception.AlohaException;
import java.lang.reflect.Method;

public class GenericSubscriber implements Subscriber {

    private Channel channel;
    private Object target;
    private Method method;
    private MessageBus messageBus;
    private ExceptionHandler exceptionHandler;
    private ExecutionStrategy executionStrategy;

    protected GenericSubscriber(Channel channel, Object target, Method method, MessageBus messageBus,
        ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.channel = channel;
        this.target = target;
        this.method = method;
        this.messageBus = messageBus;
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
    public Channel getChannel() {
        return channel;
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
    public MessageBus getMessageBus() {
        return messageBus;
    }

    @Override
    public void accept(Listener listener, Object value) throws Exception {
        try {
            this.invoke(listener, value);
        } catch (Exception exception) {
            this.handleException(exception, listener, value);
        }
    }

    protected void invoke(Listener listener, Object message) throws Exception {
        this.executionStrategy.execute(this, () -> {
            try {
                if (this.getMethod().getParameterTypes() != null) {
                    this.getMethod().invoke(this.getTarget(), message);
                } else {
                    this.getMethod().invoke(this.getTarget());
                }
            } catch (Exception e) {
                try {
                    this.handleException(e, listener, message);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected void handleException(Exception exception, Listener listener, Object value) throws Exception {
        try {
            if (this.getExceptionHandler() != null) {
                this.getExceptionHandler().handle(listener.getChannel(), value, listener.getMessageBus(), exception);
            } else {
                listener.handleException(exception, value);
            }
        } catch (Exception e) {
            listener.handleException(e, value);
        }
    }
}
