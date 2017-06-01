package sample.event.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

    public void receiveMessage(String message) {
        log.info("hhhhhhh:" + message);
    }
}
