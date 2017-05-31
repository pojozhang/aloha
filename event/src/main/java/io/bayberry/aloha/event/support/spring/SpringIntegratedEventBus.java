package io.bayberry.aloha.event.support.spring;

import io.bayberry.aloha.event.AbstractMultiEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringIntegratedEventBus extends AbstractMultiEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringIntegratedEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
