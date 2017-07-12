package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.util.Reflection;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SpringEventProxy implements ApplicationListener {

    private static final Reflection reflection = new Reflection();
    private MessageBus messageBus;

    public SpringEventProxy(MessageBus messageBus) {
        this.messageBus = messageBus;
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
            Listener listener = this.messageBus.getListenerRegistry().getListener(channel);
            if (listener == null) {
                return;
            }
            listener.notifyAll(source);
        });
    }

    private List<Channel> getCandidateChannels(Class messageType) {
        List<Channel> channels = new ArrayList<>();
        channels.add(this.messageBus.getChannelResolver().resolve(messageType));
        channels.addAll(reflection.getAllInterfaces(messageType).stream().map(this::resolveChannel).collect(toList()));
        reflection.getAllSuperClasses(messageType).forEach(superClass -> {
            channels.add(this.resolveChannel(superClass));
            channels
                .addAll(reflection.getAllInterfaces(superClass).stream().map(this::resolveChannel).collect(toList()));
        });
        return channels;
    }

    private Channel resolveChannel(Class eventType) {
        return this.messageBus.getChannelResolver().resolve(eventType);
    }
}