package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class GenericExecutionStrategy implements ExecutionStrategy {

    private Map<Subscriber, ThreadPoolExecutor> pools = new ConcurrentHashMap<>();

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        Executor executor = subscriber.getMethod().getAnnotation(Executor.class);
        if (executor != null && executor.maxCount() > 1) {
            getOrCreateThreadPoolExecutor(subscriber, executor).execute(runnable);
        } else {
            runnable.run();
        }
    }

    private ThreadPoolExecutor getOrCreateThreadPoolExecutor(Subscriber subscriber, Executor executor) {
        ThreadPoolExecutor pool = pools.get(subscriber);
        if (pool == null) {
            ThreadPoolExecutor newPool = new BlockingThreadPoolExecutor(executor.minCount(), executor.maxCount(),
                executor.capacity(), executor.keepAliveTime(), executor.unit());
            pool = pools
                .putIfAbsent(subscriber, newPool);
            if (pool == null) {
                pool = newPool;
                System.out.println("new pool");
            }
        }
        return pool;
    }
}
