package io.bayberry.aloha.spring.redis;

import io.bayberry.aloha.ConsumableChannel;

public interface RedisProxy {

    ConsumableChannel channel();
}
