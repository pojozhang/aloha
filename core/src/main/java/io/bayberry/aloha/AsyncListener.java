package io.bayberry.aloha;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncListener extends Listener {

    protected final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    public AsyncListener(final String channel, final EventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    protected void onStart() {
        this.threadPool.execute(this::listen);
    }

    @Override
    protected void onDestroy() {
        this.threadPool.shutdown();
    }

    protected abstract void listen();
}
