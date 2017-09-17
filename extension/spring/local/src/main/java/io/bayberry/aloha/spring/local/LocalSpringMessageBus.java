package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.UnsupportedMessageException;
import io.bayberry.aloha.spring.SpringListenerResolver;
import io.bayberry.aloha.spring.local.annotation.SpringEventListeners;
import io.bayberry.aloha.util.Reflection;
import org.springframework.context.*;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class LocalSpringMessageBus extends LocalMessageBus {

    private ApplicationContext applicationContext;
    private ApplicationListenerProxy applicationListenerProxy;
    private PublishCommand publishCommand;

    public LocalSpringMessageBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationListenerProxy = new ApplicationListenerProxy();
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringEventListeners.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(applicationListenerProxy);
        this.publishCommand = new PublishCommand();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        this.applicationContext.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                ApplicationEventMulticaster.class).removeApplicationListener(this.applicationListenerProxy);
        super.onDestroy();
    }

    @Override
    protected ListenerResolver initListenerResolver() {
        return new SpringListenerResolver(this.applicationContext);
    }

    @Override
    protected Stream bindStream(Listener listener) {
        return new LocalSpringEventStream(listener.getChannel(), this);
    }

    @Override
    public void post(Message message) {
        if (message instanceof SubscribableMessage) {
            this.publishCommand.execute(message.getChannel(), message.getPayload());
        }
        throw new UnsupportedMessageException(message);
    }

    public class PublishCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            applicationContext.publishEvent(message);
        }
    }

    private class ApplicationListenerProxy implements ApplicationListener {

        @Override
        public void onApplicationEvent(ApplicationEvent event) {
            Object source;
            if (event instanceof PayloadApplicationEvent) {
                source = ((PayloadApplicationEvent) event).getPayload();
            } else {
                source = event.getSource();
            }

            this.getCandidateChannels(source.getClass()).forEach(channel -> {
                Set<Stream> streams = LocalSpringMessageBus.this.getStreamRegistry().getStreams(channel);
                if (streams == null) {
                    return;
                }
                streams.forEach(stream -> stream.notifyAll(source));
            });
        }

        private List<Channel> getCandidateChannels(Class messageType) {
            List<Channel> channels = new ArrayList<>();
            channels.add(this.resolveChannel(messageType));
            channels.addAll(Reflection.getAllInterfaces(messageType).stream().map(this::resolveChannel).collect(toList()));
            Reflection.getAllSuperClasses(messageType).forEach(superClass -> {
                channels.add(LocalSpringMessageBus.this.resolveChannel(superClass));
                channels.addAll(Reflection.getAllInterfaces(superClass).stream().map(this::resolveChannel).collect(toList()));
            });
            return channels;
        }

        private Channel resolveChannel(Class messageType) {
            return LocalSpringMessageBus.this.resolveChannel(messageType);
        }
    }
}
