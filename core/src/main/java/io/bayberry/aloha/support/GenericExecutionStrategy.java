package io.bayberry.aloha.support;

import io.bayberry.aloha.ExecutionStrategy;
import io.bayberry.aloha.Subscriber;
import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.util.BlockingThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class GenericExecutionStrategy implements ExecutionStrategy {

    @Override
    public void execute(Subscriber subscriber, Runnable runnable) {
        Executor executor = subscriber.getMethod().getAnnotation(Executor.class);
        if (executor != null && executor.maxCount() > 1) {
            ThreadPoolExecutor pool = new BlockingThreadPoolExecutor(executor.minCount(),
                executor.queueSize(), executor.keepAliveTime(), executor.unit());
            pool.execute(runnable);
        } else {
            runnable.run();
        }
    }
}
