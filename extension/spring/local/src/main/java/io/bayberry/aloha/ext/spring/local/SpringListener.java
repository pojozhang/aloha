package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SpringListener extends Listener implements ApplicationListener {

    public SpringListener(EventBus eventBus) {
        super(null, eventBus);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        String channel = getEventBus().getChannelResolver().resolve(event.getSource().getClass());
        Listener listener = getEventBus().getListenerRegistry().getListener(channel);
        if (listener == null) return;
        listener.notifyAll(event.getSource());
    }

    @Override
    protected void onStart() {

    }
}
