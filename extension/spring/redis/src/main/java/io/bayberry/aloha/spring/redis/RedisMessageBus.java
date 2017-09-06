package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.*;
import io.bayberry.aloha.exception.AlohaException;
import io.bayberry.aloha.spring.SpringListenerResolver;
import io.bayberry.aloha.spring.redis.annotation.RedisListeners;
import io.bayberry.aloha.support.AsyncReceiverDecorator;
import io.bayberry.aloha.support.PrefixChannelResolverDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RedisMessageBus extends RemoteMessageBus implements SubscribableChannel, ConsumableChannel {

    private static final RedisMessageBusOptions DEFAULT_SETTINGS = new RedisMessageBusOptions("mb:");
    private RedisTemplate<String, String> redisTemplate;
    private RedisMessageBusOptions options;
    private ApplicationContext applicationContext;
    private ProduceCommand produceCommand;
    private PublishCommand publishCommand;
    private SubscribableChannel subscribableChannel;
    private ConsumableChannel consumableChannel;

    public RedisMessageBus(ApplicationContext applicationContext) {
        this(applicationContext, DEFAULT_SETTINGS);
    }

    public RedisMessageBus(ApplicationContext applicationContext, RedisMessageBusOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
        super.onCreate();
    }

    @Override
    public void onStart() {
        this.applicationContext.getBeansWithAnnotation(RedisListeners.class).values().forEach(super::register);
        this.redisTemplate = this.applicationContext.getBean(StringRedisTemplate.class);
        this.produceCommand = new ProduceCommand();
        this.publishCommand = new PublishCommand();
        this.subscribableChannel = new RedisSubscribableChannel();
        this.consumableChannel = new RedisConsumableChannel();
        super.onStart();
    }

    @Override
    protected ListenerResolver initListenerResolver() {
        return new SpringListenerResolver(this.applicationContext);
    }

    @Override
    public ChannelResolver initChannelResolver() {
        return new PrefixChannelResolverDecorator(this.options.getChannelPrefix(), super.initChannelResolver());
    }

    @Override
    protected Receiver bindReceiver(Listener listener) {
        return new AsyncReceiverDecorator(
                new RedisReceiver(listener.getChannel(), redisTemplate, this));
    }

    @Override
    public void publish(Object message) {
        this.subscribableChannel.publish(message);
    }

    @Override
    public void publish(Channel channel, Object message) {
        this.subscribableChannel.publish(channel, message);
    }

    @Override
    public void produce(Object message) {
        this.consumableChannel.produce(message);
    }

    @Override
    public void produce(Channel channel, Object message) {
        this.consumableChannel.produce(channel, message);
    }

    @Override
    public <T> T proxy(Class<T> proxyInterface) {
        return (T) Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[]{proxyInterface}, new RedisProxyInvocationHandler());
    }

    private class ProduceCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            RedisMessageBus.this.redisTemplate.opsForList()
                    .rightPush(channel.getName(), (String) RedisMessageBus.this.getSerializer().serialize(message));
        }
    }

    public class PublishCommand implements Command {

        @Override
        public void execute(Channel channel, Object message) {
            RedisMessageBus.this.redisTemplate.convertAndSend(channel.getName(), RedisMessageBus.this.getSerializer().serialize(message));
        }
    }

    private class RedisProxyInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> returnType = method.getReturnType();
            if (returnType.isAssignableFrom(SubscribableChannel.class)) {
                return RedisMessageBus.this.subscribableChannel;
            } else if (returnType.isAssignableFrom(ConsumableChannel.class)) {
                return RedisMessageBus.this.consumableChannel;
            }
            throw new AlohaException("Redis message bus does not support " + returnType);
        }
    }

    private class RedisSubscribableChannel implements SubscribableChannel {

        @Override
        public void publish(Object message) {
            this.publish(RedisMessageBus.this.getChannelResolver().resolve(message.getClass()), message);
        }

        @Override
        public void publish(Channel channel, Object message) {
            RedisMessageBus.this.post(RedisMessageBus.this.publishCommand, channel, message);
        }
    }

    private class RedisConsumableChannel implements ConsumableChannel {

        @Override
        public void produce(Object message) {
            this.produce(RedisMessageBus.this.getChannelResolver().resolve(message.getClass()), message);
        }

        @Override
        public void produce(Channel channel, Object message) {
            RedisMessageBus.this.post(RedisMessageBus.this.produceCommand, channel, message);
        }
    }
}
