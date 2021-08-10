package yll.common.jedis;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {
	T execute(Jedis jedis);
}