package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.MessageBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalSpringMessageBusConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MessageBus messageBus() {
        return new LocalSpringMessageBus();
    }
}
