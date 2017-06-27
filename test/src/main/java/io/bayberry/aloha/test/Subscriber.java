package io.bayberry.aloha.test;

import io.bayberry.aloha.annotation.Subscribe;

import java.util.concurrent.CountDownLatch;

public class Subscriber {

    public CountDownLatch countDownLatch;

    @Subscribe
    public void onSyncEvent(SyncEvent event) {
        countDownLatch.countDown();
    }
}
