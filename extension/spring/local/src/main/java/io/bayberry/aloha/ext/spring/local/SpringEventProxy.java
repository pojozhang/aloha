package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SpringEventProxy implements ApplicationListener {

    private EventBus eventBus;

    public SpringEventProxy(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Object source;
        if (event instanceof PayloadApplicationEvent) {
            source = ((PayloadApplicationEvent) event).getPayload();
        } else {
            source = event.getSource();
        }

        this.getCandidateChannels(source.getClass()).forEach(channel -> {
            Listener listener = this.eventBus.getListenerRegistry().getListener(channel);
            if (listener == null) {
                return;
            }
            listener.notifyAll(source);
        });
    }

    //TODO
    private List<Channel> getCandidateChannels(Class eventType) {
        List<Channel> channels = new ArrayList<>();
        channels.add(this.eventBus.getChannelResolver().resolve(eventType));
        channels.addAll(Arrays.stream(eventType.getInterfaces()).map(this::resolveChannel).collect(toList()));
        //add all interfaces to channels
        return channels;
    }

    private Channel resolveChannel(Class eventType) {
        return this.eventBus.getChannelResolver().resolve(eventType);
    }
}
