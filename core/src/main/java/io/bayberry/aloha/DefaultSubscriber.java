package io.bayberry.aloha;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultSubscriber extends AbstractSubscriber {

    public DefaultSubscriber(Object target, Method method) {
        super(target, method);
    }

    @Override
    public void invoke(Object value) throws InvocationTargetException, IllegalAccessException {
        if (this.getMethod().getParameterTypes() != null) {
            Object parsedObject = JSON
                    .parseObject((String) value, this.getMethod().getParameterTypes()[0]);
            this.getMethod().invoke(this.getTarget(), parsedObject);
        } else {
            this.getMethod().invoke(this.getTarget());
        }
    }
}
