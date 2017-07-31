package io.bayberry.aloha;

public interface Command {

    void execute(Channel channel, Object message);
}
