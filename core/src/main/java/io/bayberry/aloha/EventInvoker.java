package io.bayberry.aloha;

import java.util.List;

public interface EventInvoker {

    void invoke(List<Invocation> invocations, String message);
}
