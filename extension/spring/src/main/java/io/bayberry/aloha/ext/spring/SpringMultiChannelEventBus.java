package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.AbstractMultiChannelEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringMultiChannelEventBus extends AbstractMultiChannelEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringMultiChannelEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
