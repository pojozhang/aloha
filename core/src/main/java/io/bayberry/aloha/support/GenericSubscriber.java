package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.exception.AlohaException;
import java.lang.reflect.Method;

public class GenericSubscriber implements Subscriber {

    private String channel;
    private Object target;
    private Method method;
    private Listener listener;
    private ExceptionHandler exceptionHandler;
    private ExecutionStrategy executionStrategy;

    protected GenericSubscriber(String channel, Object target, Method method,
        ExceptionHandler exceptionHandler, ExecutionStrategy executionStrategy) {
        this.channel = channel;
        this.target = target;
        this.method = method;
        this.exceptionHandler = exceptionHandler;
        this.executionStrategy = executionStrategy;
//        if (options.threads > 0) {
//            this.threadPool = new BlockingThreadPoolExecutor(options.threads, options.threads, 0,
//                TimeUnit.MILLISECONDS);
//        } else {
//            threadPool = null;
//        }
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler == null ? getListener().getEventBus().getDefaultExceptionHandler() : exceptionHandler;
    }

    @Override
    public ExecutionStrategy getExecutionStrategy() {
        return this.executionStrategy;
    }

    @Override
    public String getChannel() {
        return this.channel;
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
    public Listener getListener() {
        return listener;
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void accept(Object value) throws Exception {
        try {
            this.invoke(value);
        } catch (Exception exception) {
            this.handleException(exception, value);
        }
    }

    protected void invoke(Object event) throws Exception {
        this.executionStrategy.execute(this, () -> {
            try {
                if (this.getMethod().getParameterTypes() != null) {
                    this.getMethod().invoke(this.getTarget(), event);
                } else {
                    this.getMethod().invoke(this.getTarget());
                }
            } catch (Exception e) {
                try {
                    this.handleException(e, event);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    protected void handleException(Exception exception, Object value) throws Exception {
        if (this.getExceptionHandler() != null) {
            try {
                this.getExceptionHandler().handle(getChannel(), value, getListener().getEventBus(), exception);
            } catch (Exception e) {
                this.getListener().handleException(e, value);
            }
        }
    }
}
