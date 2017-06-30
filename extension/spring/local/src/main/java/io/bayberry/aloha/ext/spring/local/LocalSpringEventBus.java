package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.local.annotation.SpringSubscriber;
import io.bayberry.aloha.support.GenericLocalEventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class LocalSpringEventBus extends GenericLocalEventBus {

    private final ApplicationContext applicationContext;
    private final SpringEventDispatcher springEventDispatcher;

    public LocalSpringEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springEventDispatcher = new SpringEventDispatcher(this);
    }

    @Override
    public Listener bindListener(Channel channel) {
        return new LocalSpringListener(channel, this);
    }

    @Override
    public void post(Object event) {
        this.post(null, event);
    }

    @Override
    public void post(Channel channel, Object event) {
        this.applicationContext.publishEvent(event);
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringSubscriber.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventDispatcher);
        super.onStart();
    }
}
