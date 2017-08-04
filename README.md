# Aloha  
[![Build Status](https://travis-ci.org/pojozhang/aloha.svg?branch=master)](https://travis-ci.org/pojozhang/aloha)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.bayberry/aloha-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.bayberry/aloha-core)

#### Aloha is a message bus facade written in Java. You can simply extend it to implement your own facade.

Of course, like any other libraries, some built-in message bus facade are included in the extension package which are listed below. 

# Aloha Spring
An aloha extension package provides easy integration with **spring boot**. For now we provide two kinds of message buses listed below.
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
RedisMessageBus is based on redis template, so make sure that you've imported **spring-boot-starter-data-redis** lib to your project.
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

