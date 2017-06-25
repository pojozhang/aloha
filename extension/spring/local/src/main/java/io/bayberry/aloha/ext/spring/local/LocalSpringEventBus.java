package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.local.annotation.SpringSubscriber;
import io.bayberry.aloha.support.GenericLocalEventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class LocalSpringEventBus extends GenericLocalEventBus {

    private final ApplicationContext applicationContext;
    private final LocalSpringListener localSpringListener;

    public LocalSpringEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.localSpringListener = new LocalSpringListener(this);
    }

    @Override
    protected Listener bindListener(String channel) {
        return localSpringListener;
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
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(localSpringListener);
        this.applicationContext.getBeansWithAnnotation(SpringSubscriber.class).values().forEach(super::register);
    }
}
