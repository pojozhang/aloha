package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.LocalMessageBus;
import io.bayberry.aloha.Receiver;
import io.bayberry.aloha.ext.spring.local.annotation.SpringListeners;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

public class LocalSpringMessageBus extends LocalMessageBus {

    private ApplicationContext applicationContext;
    private SpringEventProxy springEventProxy;

    public LocalSpringMessageBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springEventProxy = new SpringEventProxy(this);
        this.onCreate();
    }

    @Override
    public void produce(Object message) {
        this.produce(null, message);
    }

    @Override
    public void produce(Channel channel, Object message) {
        this.applicationContext.publishEvent(message);
    }

    @Override
    public void publish(Channel channel, Object message) {
        //TODO not implemented
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringListeners.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventProxy);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        this.applicationContext.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                ApplicationEventMulticaster.class).removeApplicationListener(this.springEventProxy);
        super.onDestroy();
    }

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return new LocalSpringReceiver(listener.getChannel(), this);
    }
}
