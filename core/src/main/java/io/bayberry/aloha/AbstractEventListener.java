package io.bayberry.aloha;

import java.util.List;

public abstract class AbstractEventListener implements EventListener {

    protected List<Subscriber> subscribers;

    public AbstractEventListener(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public void notifyAll(Object value) {
        this.subscribers.forEach(subscriber -> {
            try {
                subscriber.invoke(value);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        });
    }
}
