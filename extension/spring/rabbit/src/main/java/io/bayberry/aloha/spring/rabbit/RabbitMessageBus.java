package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;

public class RabbitMessageBus extends RemoteMessageBus<Object, byte[]> {

    private ApplicationContext applicationContext;
    private ConnectionFactory connectionFactory;
    private RabbitTemplate rabbitTemplate;
    private RabbitAdmin rabbitAdmin;
    private RabbitStreamContainer rabbitStreamContainer;

    public RabbitMessageBus(ApplicationContext applicationContext, ConnectionFactory connectionFactory) {
        this.applicationContext = applicationContext;
        this.connectionFactory = connectionFactory;
        this.onCreate();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.rabbitTemplate = new RabbitTemplate(this.connectionFactory);
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
