package sample.event.springboot;

import io.bayberry.aloha.event.Event;
import io.bayberry.aloha.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
//    @Autowired
//    EventBus eventBus;


    
    @GetMapping
    public void tt(){
//        eventBus.post(new Event<Object>() {
//        });
    }
}
