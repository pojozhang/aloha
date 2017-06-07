package io.bayberry.aloha.support;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.exception.AlohaException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncListener extends Listener {

    protected final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    public AsyncListener(final String channel, final EventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    protected void onStart() {
        this.threadPool.execute(() -> {
            try {
                this.listen();
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
    protected void onDestroy() {
        this.threadPool.shutdown();
    }

    protected abstract void listen();
}
