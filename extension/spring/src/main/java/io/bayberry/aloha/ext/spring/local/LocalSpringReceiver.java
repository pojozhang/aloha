package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.LocalMessageBus;
import io.bayberry.aloha.LocalReceiver;

public class LocalSpringReceiver extends LocalReceiver {

    public LocalSpringReceiver(Channel channel, LocalMessageBus messageBus) {
        super(channel, messageBus);
        this.onCreate();
    }

    @Override
    protected void onStart() {
    }
}
