package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

import java.util.List;

public class SpringEventDispatcher implements ApplicationListener {

    private EventBus eventBus;

    public SpringEventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        List<String> channels;
        Object source;
        if (event instanceof PayloadApplicationEvent) {
            channels = resolveChannelsFromPayloadApplicationEvent((PayloadApplicationEvent) event);
            source = ((PayloadApplicationEvent) event).getPayload();
        } else {
            channels = resolveChannelsFromApplicationEvent(event);
            source = event.getSource();
        }
        if (channels == null) return;
        channels.forEach(channel -> {
            Listener listener = this.eventBus.getListenerRegistry().getListener(channel);
            if (listener == null) return;
            listener.notifyAll(source);
        });
    }

    private List<String> resolveChannelsFromPayloadApplicationEvent(PayloadApplicationEvent event) {
        return this.resolveChannels(event.getPayload().getClass());
    }

    private List<String> resolveChannelsFromApplicationEvent(ApplicationEvent event) {
        return this.resolveChannels(event.getSource().getClass());
    }

    private List<String> resolveChannels(Class eventType) {
        return this.eventBus.getChannelResolver().resolve(eventType);
    }
}
