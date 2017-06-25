package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

public class SpringEventDispatcher implements ApplicationListener {

    private EventBus eventBus;

    public SpringEventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        String channel;
        Object source;
        if (event instanceof PayloadApplicationEvent) {
            channel = resolveChannelFromPayloadApplicationEvent((PayloadApplicationEvent) event);
            source = ((PayloadApplicationEvent) event).getPayload();
        } else {
            channel = resolveChannelFromApplicationEvent(event);
            source = event.getSource();
        }
        Listener listener = this.eventBus.getListenerRegistry().getListener(channel);
        if (listener == null) return;
        listener.notifyAll(source);
    }

    private String resolveChannelFromApplicationEvent(ApplicationEvent event) {
        return this.resolveChannel(event.getSource().getClass());
    }

    private String resolveChannelFromPayloadApplicationEvent(PayloadApplicationEvent event) {
        return this.resolveChannel(event.getPayload().getClass());
    }

    private String resolveChannel(Class eventType) {
        return this.eventBus.getChannelResolver().resolve(eventType);
    }
}
