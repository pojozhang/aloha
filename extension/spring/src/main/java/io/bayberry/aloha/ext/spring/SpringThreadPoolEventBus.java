package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.support.GenericEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringThreadPoolEventBus extends GenericEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringThreadPoolEventBus(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
