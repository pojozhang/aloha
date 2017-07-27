package io.bayberry.aloha;

import java.lang.reflect.Method;

public interface ChannelResolver {

    Channel resolve(Class messageType);

    Channel resolve(Method listenerMethod);
}
