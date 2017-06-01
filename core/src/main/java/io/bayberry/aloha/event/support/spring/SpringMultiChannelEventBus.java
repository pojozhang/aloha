package io.bayberry.aloha.event.support.spring;

import io.bayberry.aloha.event.AbstractMultiChannelEventBus;
import org.springframework.context.ApplicationContext;

public abstract class SpringMultiChannelEventBus extends AbstractMultiChannelEventBus {

    protected final ApplicationContext applicationContext;

    protected SpringMultiChannelEventBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
