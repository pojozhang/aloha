package io.bayberry.aloha.support;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.exception.AlohaException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncListenerDecorator extends DelegateListener {

    private ExecutorService threadPool = Executors.newSingleThreadExecutor();

    public AsyncListenerDecorator(Listener delegate) {
        super(delegate);
    }

    @Override
    public void start() {
        this.threadPool.execute(() -> {
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
        this.threadPool.shutdown();
        super.shutdown();
    }
}
