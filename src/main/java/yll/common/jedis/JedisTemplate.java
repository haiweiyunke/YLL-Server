package yll.common.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTemplate {

	// ==============================Fields===========================================
	private final JedisPool pool;

	//===============================Constructors=====================================
	public JedisTemplate(JedisPool pool) {
		this.pool = pool;
	}

	// ==============================Methods==========================================
	public <T> T execute(JedisCallback<T> callback) {
		Jedis jedis = pool.getResource();
		try {
			return callback.execute(jedis);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public JedisPool get() {
		return pool;
	}

}
