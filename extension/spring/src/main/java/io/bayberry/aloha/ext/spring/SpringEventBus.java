package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.support.GenericEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringEventBus extends GenericEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringEventBus(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
