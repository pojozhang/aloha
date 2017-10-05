package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.MessageBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisMessageBusConfig {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MessageBus messageBus() {
        return new RedisMessageBus(connectionFactory);
    }
}
