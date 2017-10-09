package io.bayberry.aloha.util;

import io.bayberry.aloha.exception.UncheckedAlohaException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {

    public BlockingThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final int maximumQueueSize,
        final long keepAliveTime, final TimeUnit unit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>(maximumQueueSize),
            (runnable, executor) -> {
                try {
                    executor.getQueue().put(runnable);
                } catch (InterruptedException e) {
                    throw new UncheckedAlohaException(e);
                }
            });
    }
}
