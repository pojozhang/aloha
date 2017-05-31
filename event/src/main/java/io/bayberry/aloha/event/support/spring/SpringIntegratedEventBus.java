package io.bayberry.aloha.event.support.spring;

import io.bayberry.aloha.event.AbstractEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringIntegratedEventBus extends AbstractEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringIntegratedEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
