package io.bayberry.aloha;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class MultiChannelSubscriberInvoker extends AbstractSubscriberInvoker implements SubscriberInvoker, Runnable {

    protected String channel;
}
