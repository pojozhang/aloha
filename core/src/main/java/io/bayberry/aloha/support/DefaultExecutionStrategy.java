package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.annotation.Concurrency;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class DefaultExecutionStrategy implements ExecutionStrategy {

    private Map<Listener, ThreadPoolExecutor> pools = new ConcurrentHashMap<>();

    @Override
    public void execute(Listener listener, Runnable runnable) {
        Concurrency concurrency = listener.getMethod().getAnnotation(Concurrency.class);
        if (concurrency != null && concurrency.maxCount() > 1) {
            getOrCreateThreadPoolExecutor(listener, concurrency).execute(runnable);
        } else {
            runnable.run();
        }
    }

    private ThreadPoolExecutor getOrCreateThreadPoolExecutor(Listener listener, Concurrency concurrency) {
        ThreadPoolExecutor pool = pools.get(listener);
        if (pool == null) {
            ThreadPoolExecutor newPool = new BlockingThreadPoolExecutor(concurrency.minCount(), concurrency.maxCount(),
                    concurrency.capacity(), concurrency.keepAliveTime(), concurrency.unit());
            pool = pools
                    .putIfAbsent(listener, newPool);
            if (pool == null) {
                pool = newPool;
            }
        }
        return pool;
    }
}
