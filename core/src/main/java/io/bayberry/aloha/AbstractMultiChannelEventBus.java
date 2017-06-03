package io.bayberry.aloha;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractMultiChannelEventBus extends AbstractEventBus implements MultiChannelEventBus {

    private ExecutorService threadPool;

    @Override
    public MultiChannelSubscriberRegistry getSubscriberRegistry() {
        return (MultiChannelSubscriberRegistry) super.getSubscriberRegistry();
    }

    @Override
    protected MultiChannelSubscriberRegistry newRegistryInstance() {
        return new DefaultMultiChannelSubscriberRegistry(this);
    }

    @Override
    public void onStart() {
        Set<String> channels = this.getSubscriberRegistry().getChannels();
        this.threadPool = Executors.newFixedThreadPool(channels.size());
        channels.forEach(channel -> threadPool.execute(() -> this.getEventListener(channel, ((MultiChannelSubscriberRegistry) super.subscriberRegistry).getSubscribers(channel)).start()));
    }

    @Override
    public void onDestroy() {
        this.threadPool.shutdown();
    }
}
