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

    public void set(String key, String value, int seconds) {
        set(key, value);
        expire(key, seconds);
    }

    public void set(String key, String value) {
        if (value == null)
            getJedis().del(key);
        else
            getJedis().set(key, value);
    }

    public String get(String key) {
        return getJedis().get(key);
    }

    public long expire(String key, int seconds) {
        return getJedis().expire(key, seconds);
    }
}