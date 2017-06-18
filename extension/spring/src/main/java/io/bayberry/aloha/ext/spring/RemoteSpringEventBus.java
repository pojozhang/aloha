package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.support.GenericRemoteEventBus;
import org.springframework.context.ApplicationContext;

public abstract class RemoteSpringEventBus extends GenericRemoteEventBus {

    protected final ApplicationContext applicationContext;

    protected RemoteSpringEventBus(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
