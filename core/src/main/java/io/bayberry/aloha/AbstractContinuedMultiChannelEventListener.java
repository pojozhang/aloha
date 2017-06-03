package io.bayberry.aloha;

import java.util.List;

public abstract class AbstractContinuedMultiChannelEventListener extends AbstractMultiChannelEventListener {

    public AbstractContinuedMultiChannelEventListener(String channel, List<Subscriber> subscribers) {
        super(channel, subscribers);
    }

    @Override
    public final void start() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.run();
            } catch (Exception e) {
            }
        }
    }

    protected abstract void run();
}
