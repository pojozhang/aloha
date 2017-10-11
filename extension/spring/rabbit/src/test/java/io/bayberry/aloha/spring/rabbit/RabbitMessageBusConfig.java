package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.MessageBus;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMessageBusConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MessageBus messageBus() {
        return new RabbitMessageBus(connectionFactory);
    }
}
