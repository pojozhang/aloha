package io.bayberry.aloha.ext.spring.data.redis;

public class RedisEventBusSettingsBuilder {

    private String channelPrefix;

    public RedisEventBusSettingsBuilder channelPrefix(String channelPrefix) {
        this.channelPrefix = channelPrefix;
        return this;
    }

    public RedisEventBusSettings build() {
        RedisEventBusSettings redisEventBusSettings = new RedisEventBusSettings();
        redisEventBusSettings.setChannelPrefix(this.channelPrefix);
        return redisEventBusSettings;
    }
}
