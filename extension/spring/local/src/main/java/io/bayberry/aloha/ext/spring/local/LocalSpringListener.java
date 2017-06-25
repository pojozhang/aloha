package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.LocalEventBus;
import io.bayberry.aloha.LocalListener;

public class LocalSpringListener extends LocalListener {

    public LocalSpringListener(String channel, LocalEventBus eventBus) {
        super(channel, eventBus);
    }

    @Override
    protected void onStart() {
    }
}
