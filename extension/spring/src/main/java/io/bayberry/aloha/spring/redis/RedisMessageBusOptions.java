package io.bayberry.aloha.spring.redis;

public class RedisMessageBusOptions {

    private String channelPrefix;

    public RedisMessageBusOptions() {
    }

    public RedisMessageBusOptions(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }

    public String getChannelPrefix() {
        return channelPrefix;
    }

    public void setChannelPrefix(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }
}
