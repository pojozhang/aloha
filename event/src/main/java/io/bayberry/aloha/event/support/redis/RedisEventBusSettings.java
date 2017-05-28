package io.bayberry.aloha.event.support.redis;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedisEventBusSettings {

    private final String namespace;
}
