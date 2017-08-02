package io.bayberry.aloha.ext.spring.local;

import static java.util.stream.Collectors.toList;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.util.Reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

public class SpringEventProxy implements ApplicationListener {

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
            Set<Receiver> receivers = this.messageBus.getReceiverRegistry().getReceivers(channel);
            if (receivers == null) {
                return;
            }
            receivers.forEach(receiver -> receiver.notifyAll(source));
        });
    }

    private List<Channel> getCandidateChannels(Class messageType) {
        List<Channel> channels = new ArrayList<>();
        channels.add(this.messageBus.getChannelResolver().resolve(messageType));
        channels.addAll(Reflection.getAllInterfaces(messageType).stream().map(this::resolveChannel).collect(toList()));
        Reflection.getAllSuperClasses(messageType).forEach(superClass -> {
            channels.add(this.resolveChannel(superClass));
            channels
                    .addAll(Reflection.getAllInterfaces(superClass).stream().map(this::resolveChannel).collect(toList()));
        });
        return channels;
    }

    private Channel resolveChannel(Class eventType) {
        return this.messageBus.getChannelResolver().resolve(eventType);
    }
}
