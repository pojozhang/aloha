package io.bayberry.aloha.support;

import io.bayberry.aloha.ExceptionHandler;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GenericSubscriber extends Subscriber {

    private final ThreadPoolExecutor threadPool;

    protected GenericSubscriber(String channel, Object target, Method method,
                                ExceptionHandler exceptionHandler, GenericSubscriberOptions options) {
        super(channel, target, method, exceptionHandler);
        if (options.threads > 0) {
            this.threadPool = new BlockingThreadPoolExecutor(options.threads, options.threads, 0,
                    TimeUnit.MILLISECONDS);
        } else {
            threadPool = null;
        }
    }

    @Override
    public void invoke(Object event) throws Exception {
        if (this.threadPool != null) {
            this.threadPool.execute(getRunnable(event));
        } else {
            getRunnable(event).run();
        }
    }

    private Runnable getRunnable(Object event) {
        return () -> {
            try {
                super.invoke(event);
            } catch (Exception e) {
                try {
                    this.handleException(e, event);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        };
    }
}
