package io.bayberry.aloha.support;

import io.bayberry.aloha.Stream;
import io.bayberry.aloha.exception.UncheckedAlohaException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncStreamDecorator extends DelegateStream {

    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private Future task;

    public AsyncStreamDecorator(Stream delegate) {
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
                    throw new UncheckedAlohaException(error);
                }
            }
        });
    }

    @Override
    public void stop() {
        this.task.cancel(true);
        this.threadPool.shutdown();
        try {
            this.threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
        super.stop();
    }
}
