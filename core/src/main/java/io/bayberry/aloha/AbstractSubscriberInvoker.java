package io.bayberry.aloha;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractSubscriberInvoker implements SubscriberInvoker {

    @Override
    public void invoke(List<SubscriberInvocation> invocations, String message) {
        invocations.forEach(invocation -> {
            try {
                if (invocation.getMethod().getParameterTypes() != null) {
                    Object parsedObject = JSON
                            .parseObject(message, invocation.getMethod().getParameterTypes()[0]);
                    invocation.getMethod().invoke(invocation.getSubscriber(), parsedObject);
                } else {
                    invocation.getMethod().invoke(invocation.getSubscriber());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
