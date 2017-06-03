package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface Subscriber {

    void invoke(Object value) throws Exception;

    Object getTarget();

    Method getMethod();
}
