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
        this.threadPool = new BlockingThreadPoolExecutor(options.threads, options.threads, 0,
            TimeUnit.MILLISECONDS);
    }

    @Override
    public void invoke(Object event) throws Exception {
        this.threadPool.execute(() -> {
            try {
                super.invoke(event);
            } catch (Exception e) {
                try {
                    this.handleException(e, event);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }
}
