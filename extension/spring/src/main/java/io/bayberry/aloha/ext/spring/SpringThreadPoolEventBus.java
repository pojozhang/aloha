package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.support.AbstractGenericEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringThreadPoolEventBus extends AbstractGenericEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringThreadPoolEventBus(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
