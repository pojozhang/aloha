package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GenericSubscriber extends Subscriber {

    private final ThreadPoolExecutor threadPool;

    protected GenericSubscriber(final Object target, final Method method, final String channel, final ExceptionHandler exceptionHandler, final Settings settings) {
        super(target, method, channel, exceptionHandler);
        this.threadPool = new BlockingThreadPoolExecutor(settings.getThreads(), settings.getThreads(), 0,
                TimeUnit.MILLISECONDS);
    }

    @Override
    public void invoke(Object event) throws Exception {
        this.threadPool.execute(() -> {
            try {
                this.invokeSubscriberMethodWithArgument(event);
            } catch (InvocationTargetException | IllegalAccessException e) {
                try {
                    this.handleException(e, event);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    private void invokeSubscriberMethodWithArgument(Object event)
            throws InvocationTargetException, IllegalAccessException {
        if (this.getMethod().getParameterTypes() != null) {
            this.getMethod().invoke(this.getTarget(), event);
        } else {
            this.getMethod().invoke(this.getTarget());
        }
    }

    public static class Settings {

        private int threads;
        private Class<? extends ExceptionHandler> exceptionHandlerType;

        public int getThreads() {
            return threads;
        }

        public Class<? extends ExceptionHandler> getExceptionHandlerType() {
            return exceptionHandlerType;
        }

        public static Settings parse(Subscribe subscribe) {
            Settings settings = new Settings();
            settings.threads = subscribe.threads();
            settings.exceptionHandlerType = subscribe.exceptionHandler();
            return settings;
        }
    }
}
