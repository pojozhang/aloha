package io.bayberry.aloha.test;

import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.annotation.Subscribe;
import java.util.concurrent.CountDownLatch;

public class TestSubscriber {

    public CountDownLatch countDownLatch;

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
