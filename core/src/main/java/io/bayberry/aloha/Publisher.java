package io.bayberry.aloha;

public interface Publisher {

    void publish(Object message);

    void publish(Channel channel, Object message);
}
