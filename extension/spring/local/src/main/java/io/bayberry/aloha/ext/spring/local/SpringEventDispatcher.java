package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
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
        Channel channel;
        Object source;
        if (event instanceof PayloadApplicationEvent) {
            channel = resolveChannelsFromPayloadApplicationEvent((PayloadApplicationEvent) event);
            source = ((PayloadApplicationEvent) event).getPayload();
        } else {
            channel = resolveChannelsFromApplicationEvent(event);
            source = event.getSource();
        }

        Listener listener = this.eventBus.getListenerRegistry().getListener(channel);
        if (listener == null) {
            return;
        }
        listener.notifyAll(source);
    }

    private Channel resolveChannelsFromPayloadApplicationEvent(PayloadApplicationEvent event) {
        return this.resolveChannels(event.getPayload().getClass());
    }

    private Channel resolveChannelsFromApplicationEvent(ApplicationEvent event) {
        return this.resolveChannels(event.getSource().getClass());
    }

    private Channel resolveChannels(Class eventType) {
        return this.eventBus.getChannelResolver().resolve(eventType);
    }
}
