package io.bayberry.aloha.ext.spring.redis;

public class RedisEventBusOptions {

    private String channelPrefix;

    public RedisEventBusOptions() {
    }

    public RedisEventBusOptions(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }

    public String getChannelPrefix() {
        return channelPrefix;
    }

    public void setChannelPrefix(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }
}
