package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class RabbitMessageBus extends RemoteMessageBus<Object, byte[]> implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;
    private ConnectionFactory connectionFactory;
    private RabbitTemplate rabbitTemplate;
    private RabbitAdmin rabbitAdmin;
    private RabbitStreamContainer rabbitStreamContainer;

    public RabbitMessageBus(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.rabbitTemplate = new RabbitTemplate(this.connectionFactory);
        this.rabbitTemplate.setMessageConverter(new RabbitMessageConverterBridge(this.getSerializer()));
        this.rabbitTemplate.afterPropertiesSet();
        this.rabbitAdmin = new RabbitAdmin(this.connectionFactory);
        this.rabbitAdmin.setAutoStartup(false);
        this.rabbitAdmin.afterPropertiesSet();
        this.rabbitStreamContainer = new RabbitStreamContainer();
    }

    @Override
    public void onStart() {
        this.rabbitStreamContainer.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        this.rabbitStreamContainer.stop();
        super.onStop();
    }

    @Override
    protected ChannelResolver initChannelResolver() {
        return super.initChannelResolver();
    }

    @Override
    protected Stream bindStream(Channel channel, Listener listener) {

        return null;
    }

    @Override
    public void post(Message message) {
        Channel channel = message.getChannel();
        if (channel == null) {
            channel = RabbitMessageBus.this.getChannelResolver().resolve(message.getClass());
        }
        this.rabbitTemplate.convertAndSend(channel.getName(), message.getPayload());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.onCreate();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private class RabbitStreamContainer {

        private MessageListenerContainer messageListenerContainer;

        public RabbitStreamContainer() {
            this.messageListenerContainer = new SimpleMessageListenerContainer();
        }

        public void start() {
            this.messageListenerContainer.start();
        }

        public void stop() {
            this.messageListenerContainer.stop();
        }
    }
}
