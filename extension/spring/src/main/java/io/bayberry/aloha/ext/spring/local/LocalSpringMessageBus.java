package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.local.annotation.SpringSubscriber;
import io.bayberry.aloha.support.GenericLocalMessageBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

public class LocalSpringMessageBus extends GenericLocalMessageBus {

    private final ApplicationContext applicationContext;
    private final SpringEventProxy springEventProxy;

    public LocalSpringMessageBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springEventProxy = new SpringEventProxy(this);
        this.onCreate();
    }

    @Override
    public Listener bindListener(Channel channel) {
        return new LocalSpringListener(channel, this);
    }

    @Override
    public void post(Object message) {
        this.post(null, message);
    }

    @Override
    public void post(Channel channel, Object message) {
        this.applicationContext.publishEvent(message);
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringSubscriber.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventProxy);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        this.applicationContext.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class).removeApplicationListener(this.springEventProxy);
        super.onDestroy();
    }
}
