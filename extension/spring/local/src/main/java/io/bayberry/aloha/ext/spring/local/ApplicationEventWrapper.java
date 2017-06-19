package io.bayberry.aloha.ext.spring.local;

import org.springframework.context.ApplicationEvent;

public class ApplicationEventWrapper extends ApplicationEvent {

    public ApplicationEventWrapper(Object source) {
        super(source);
    }
}
