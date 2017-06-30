package io.bayberry.aloha.test;

import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.annotation.Subscribe;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TestSubscriber {

    public CountDownLatch countDownLatch;
    private AtomicInteger c = new AtomicInteger(0);

    @Subscribe
    public void onSyncEvent(SyncEvent event) {
        countDownLatch.countDown();
    }

    @Executor(maxCount = 2)
    @Subscribe
    public void onAsyncEvent(AsyncEvent event) throws InterruptedException {
        Thread.sleep(1000);
        countDownLatch.countDown();
    }
}
