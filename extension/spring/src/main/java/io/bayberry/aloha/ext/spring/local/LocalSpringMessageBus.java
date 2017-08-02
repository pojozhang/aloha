package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.*;
import io.bayberry.aloha.ext.spring.SpringListenerResolver;
import io.bayberry.aloha.ext.spring.local.annotation.SpringEventListeners;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

public class LocalSpringMessageBus extends LocalMessageBus implements Publisher {

    private ApplicationContext applicationContext;
    private SpringEventProxy springEventProxy;
    private SpringCommand springCommand;

    public LocalSpringMessageBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.springEventProxy = new SpringEventProxy(this);
        this.onCreate();
    }

    @Override
    public void publish(Object message) {
        this.publish(null, message);
    }

    @Override
    public void publish(Channel channel, Object message) {
        super.post(springCommand, channel, message);
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(SpringEventListeners.class).values().forEach(super::register);
        ((ConfigurableApplicationContext) this.applicationContext).addApplicationListener(springEventProxy);
        this.springCommand = new SpringCommand(this.applicationContext);
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
    protected Receiver bindReceiver(Listener listener) {
        return new LocalSpringReceiver(listener.getChannel(), this);
    }
}
