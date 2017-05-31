package io.bayberry.aloha.event;

import java.util.List;

public interface EventInvoker extends Runnable {

    void invoke(List<Invocation> invocations, String message);
}
