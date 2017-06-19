package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.local.annotation.SpringSubscriber;
import io.bayberry.aloha.support.GenericEventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringEventBus extends GenericEventBus {

    private final ApplicationContext applicationContext;
    private final SpringListener springListener;

    public SpringEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springListener = new SpringListener(this);
    }

    @Override
    protected Listener bindListener(String channel) {
        return springListener;
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
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springListener);
        this.applicationContext.getBeansWithAnnotation(SpringSubscriber.class).values().forEach(super::register);
    }
}
