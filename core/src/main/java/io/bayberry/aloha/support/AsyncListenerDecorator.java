package io.bayberry.aloha.support;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.exception.AlohaException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncListenerDecorator extends DelegateListener {

    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private Future task;

    public AsyncListenerDecorator(Listener delegate) {
        super(delegate);
    }

    @Override
    public void start() {
        this.task = this.threadPool.submit(() -> {
            try {
                super.start();
            } catch (Exception exception) {
                try {
                    super.handleException(exception, null);
                } catch (Exception error) {
                    throw new AlohaException(error);
                }
            }
        });
    }

    @Override
    public void shutdown() {
        this.task.cancel(true);
        this.threadPool.shutdown();
        try {
            this.threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
        super.shutdown();
    }
}
