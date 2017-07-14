# Aloha
#### Aloha provides some generic and extensible interfaces of message bus written in Java. You can simply extend them to implement your own message bus.

Of course, like any other library, we have some built-in message buses included in the extension module, such as aloha-spring module.

# Aloha Spring
An aloha extension module to provide easy integration with **spring boot**. For new we provide two kinds of message buses listed below.
## 1. LocalSpringMessageBus
LocalSpringMessageBus is just a wrapper of Spring's ApplicationEventPublisher. You can easily post a Spring event through the interface.
### Usage
```java
MessageBus bus = new LocalSpringMessageBus(this.applicationContext);
bus.register(subscriber);
bus.post(new SomeEvent());
bus.start();
```

## 2. RedisMessageBus
RedisMessageBus is based on redis template, so make sure that you've imported **spring-boot-starter-data-redis** package to your project.
### Usage
```java
MessageBus bus = new RedisMessageBus(this.applicationContext);
bus.register(subscriber);
bus.post(new SomeEvent());
bus.start();
```

## 3. Subscriber
Besides, we also need some subscribers to subscribe channels so that we can receive messages from local or remote message buses.

### Usage
```java
@Executor(maxCount = 2)
@Subscribe
public void onMessage(Message message) {
    //handle message
}
```
The **@Executor** annotation is optional. When you use it, aloha will create muitl threads to handle your message.

