package io.bayberry.aloha.ext.spring.local;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.ext.spring.SpringEventBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class SyncEventBus extends SpringEventBus {

    protected SyncEventBus(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected Listener bindListener(String channel) {
        return null;
    }

    @Override
    public void post(String channel, String message) {

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ((ConfigurableApplicationContext) this.applicationContext).getBeanFactory().registerSingleton("TTTTT", new Object());

        this.applicationContext.toString();
    }
}
