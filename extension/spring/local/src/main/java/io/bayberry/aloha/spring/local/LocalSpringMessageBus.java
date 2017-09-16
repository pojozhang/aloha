package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.UnsupportedMessageException;
import io.bayberry.aloha.spring.SpringListenerResolver;
import io.bayberry.aloha.spring.local.annotation.SpringEventListeners;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

public class LocalSpringMessageBus extends LocalMessageBus {

    private ApplicationContext applicationContext;
    private SpringEventProxy springEventProxy;
    private PublishCommand publishCommand;

    public LocalSpringMessageBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springEventProxy = new SpringEventProxy(this);
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringEventListeners.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventProxy);
        this.publishCommand = new PublishCommand();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        this.applicationContext.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                ApplicationEventMulticaster.class).removeApplicationListener(this.springEventProxy);
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
}
