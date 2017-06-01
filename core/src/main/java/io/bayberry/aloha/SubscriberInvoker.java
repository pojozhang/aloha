package io.bayberry.aloha;

import java.util.List;

public interface SubscriberInvoker {

    void invoke(List<SubscriberInvocation> invocations, String message);
}
