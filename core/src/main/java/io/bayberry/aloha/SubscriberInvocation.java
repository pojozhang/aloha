package io.bayberry.aloha;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class SubscriberInvocation {

    private Object subscriber;
    private Method method;
}
