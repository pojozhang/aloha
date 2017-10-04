package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.Listener;
import io.bayberry.aloha.Message;
import io.bayberry.aloha.Stream;
import io.bayberry.aloha.RemoteMessageBus;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;

public class RabbitMessageBus extends RemoteMessageBus {

    private ApplicationContext applicationContext;
    private ConnectionFactory connectionFactory;

    public RabbitMessageBus(ApplicationContext applicationContext, ConnectionFactory connectionFactory) {
        this.applicationContext = applicationContext;
        this.connectionFactory = connectionFactory;
        this.onCreate();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected Stream bindStream(Listener listener) {
        return null;
    }

    @Override
    public void post(Message message) {

    }
}
