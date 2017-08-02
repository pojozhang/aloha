package io.bayberry.aloha.spring.local;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Command;
import org.springframework.context.ApplicationContext;

public class SpringCommand implements Command {

    private ApplicationContext applicationContext;

    public SpringCommand(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void execute(Channel channel, Object message) {
        this.applicationContext.publishEvent(message);
    }
}
