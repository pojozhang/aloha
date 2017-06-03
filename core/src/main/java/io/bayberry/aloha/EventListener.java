package io.bayberry.aloha;

import java.util.List;

public interface EventListener {

    void start();

    void notify(List<Subscriber> subscribers, Object content);
}
