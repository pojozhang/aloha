package io.bayberry.aloha.support;

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

    protected GenericSubscriber(final Object target, final Method method, final String channel, final Settings settings) {
        super(target, method, channel);
        this.threadPool = new BlockingThreadPoolExecutor(settings.getThreads(), settings.getThreads(), 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public void invoke(Object value) {
        threadPool.execute(() -> {
            try {
                this.invokeSubscriberMethodWithArgument(value);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new AlohaException(e);
            }
        });
    }

    private void invokeSubscriberMethodWithArgument(Object value) throws InvocationTargetException, IllegalAccessException {
        if (this.getMethod().getParameterTypes() != null) {
            this.getMethod().invoke(this.getTarget(), value);
        } else {
            this.getMethod().invoke(this.getTarget());
        }
    }

    public static class Settings {

        private int threads;

        public int getThreads() {
            return threads;
        }

        public static Settings parse(Subscribe subscribe) {
            Settings settings = new Settings();
            settings.threads = subscribe.threads();
            return settings;
        }
    }
}
