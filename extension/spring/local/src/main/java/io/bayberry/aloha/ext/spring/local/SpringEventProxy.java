package io.bayberry.aloha.ext.spring.local;

import static java.util.stream.Collectors.toList;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.EventBus;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.util.Reflection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

public class SpringEventProxy implements ApplicationListener {

    private static final Reflection reflection = new Reflection();
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

    private List<Channel> getCandidateChannels(Class eventType) {
        List<Channel> channels = new ArrayList<>();
        channels.add(this.eventBus.getChannelResolver().resolve(eventType));
        channels.addAll(reflection.getAllInterfaces(eventType).stream().map(this::resolveChannel).collect(toList()));
        reflection.getAllSuperClasses(eventType).forEach(superClass -> {
            channels.add(this.resolveChannel(superClass));
            channels
                .addAll(reflection.getAllInterfaces(superClass).stream().map(this::resolveChannel).collect(toList()));
        });
        return channels;
    }

    private Channel resolveChannel(Class eventType) {
        return this.eventBus.getChannelResolver().resolve(eventType);
    }
}
