package io.bayberry.aloha.ext.spring.local;

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
    protected Listener bindListener(String channel) {
        return new LocalSpringListener(channel, this);
    }

    @Override
    public void post(Object event) {
        this.post(null, event);
    }

    @Override
    public void post(String channel, Object event) {
        this.applicationContext.publishEvent(event);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.applicationContext.getBeansWithAnnotation(SpringSubscriber.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventDispatcher);
    }
}
