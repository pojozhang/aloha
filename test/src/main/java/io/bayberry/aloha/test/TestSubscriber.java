package io.bayberry.aloha.test;

import io.bayberry.aloha.annotation.Executor;
import io.bayberry.aloha.annotation.Consume;
import java.util.concurrent.CountDownLatch;

public class TestSubscriber {

    public CountDownLatch countDownLatch;

    @Consume
    public void onSyncMessage(SyncMessage message) {
        countDownLatch.countDown();
    }

    @Executor(maxCount = 2)
    @Consume
    public void onAsyncMessage(AsyncMessage message) throws InterruptedException {
        Thread.sleep(1000);
        countDownLatch.countDown();
    }
}
