package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.LocalEventBus;
import io.bayberry.aloha.LocalListener;

public class LocalSpringListener extends LocalListener {

    public LocalSpringListener(Channel channel, LocalEventBus eventBus) {
        super(channel, eventBus);
        this.onCreate();
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onDestroy() {
    }
}
