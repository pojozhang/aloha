package io.bayberry.aloha.ext.spring.redis;

public class RedisEventBusSettings {

    private String channelPrefix;

    public String getChannelPrefix() {
        return channelPrefix;
    }

    public void setChannelPrefix(String channelPrefix) {
        this.channelPrefix = channelPrefix;
    }
}
