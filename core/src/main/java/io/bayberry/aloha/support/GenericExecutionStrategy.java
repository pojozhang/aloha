package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class GenericExecutionStrategy implements ExecutionStrategy {

    private Map<Listener, ThreadPoolExecutor> pools = new ConcurrentHashMap<>();

    @Override
    public void execute(Listener listener, Runnable runnable) {
        Executor executor = listener.getMethod().getAnnotation(Executor.class);
        if (executor != null && executor.maxCount() > 1) {
            getOrCreateThreadPoolExecutor(listener, executor).execute(runnable);
        } else {
            runnable.run();
        }
    }

    private ThreadPoolExecutor getOrCreateThreadPoolExecutor(Listener listener, Executor executor) {
        ThreadPoolExecutor pool = pools.get(listener);
        if (pool == null) {
            ThreadPoolExecutor newPool = new BlockingThreadPoolExecutor(executor.minCount(), executor.maxCount(),
                    executor.capacity(), executor.keepAliveTime(), executor.unit());
            pool = pools
                    .putIfAbsent(listener, newPool);
            if (pool == null) {
                pool = newPool;
            }
        }
        return pool;
    }
}
