package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.support.GenericRemoteMessageBus;
import org.springframework.context.ApplicationContext;

public abstract class RemoteSpringMessageBus extends GenericRemoteMessageBus {

    protected final ApplicationContext applicationContext;

    protected RemoteSpringMessageBus(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
