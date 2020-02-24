package com.ggboy.framework.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisWrapper {
    private final JedisPool jedisPool;

    public RedisWrapper(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void set(String key, String value) {
        getJedis().set(key, value);
    }

    public String get(String key) {
        return getJedis().get(key);
    }
}