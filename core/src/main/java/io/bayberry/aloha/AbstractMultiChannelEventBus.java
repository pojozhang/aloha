package io.bayberry.aloha;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractMultiChannelEventBus extends AbstractLifeCycleEventBus implements MultiChannelEventBus {

    private MultiChannelSubscriberRegistry registry;
    private ExecutorService pool;

    @Override
    protected MultiChannelSubscriberRegistry registry() {
        if (registry == null) {
            registry = new DefaultMultiChannelSubscriberRegistry(this);
        }
        return registry;
    }

    @Override
    public void onStart() {
        Set<String> channels = registry().getChannels();
        this.pool = Executors.newFixedThreadPool(channels.size());
        channels.forEach(channel -> pool.execute(getSubscriberInvoker(channel)));
    }

    @Override
    public void onDestroy() {
        this.pool.shutdown();
    }

    protected abstract MultiChannelSubscriberInvoker getSubscriberInvoker(String channel);
}
