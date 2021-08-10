package yll.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import yll.common.jedis.JedisTemplate;
import yll.component.util.JedisUtils;

@Configuration
public class RedisConfiguration {

	// ==============================Fields===========================================
	@Value("${redis.host}")
	private String host = Protocol.DEFAULT_HOST;

	@Value("${redis.port:6379}")
	private int port = Protocol.DEFAULT_PORT;

	@Value("${redis.password:#{null}}")
	private String password;

	@Value("${redis.timeout:2000}")
	private int timeout = 2000;

	@Value("${redis.database:0}")
	private int database = 0;

	@Value("${redis.pool.max-active:8}")
	private int maxActive = 8;

	@Value("${redis.pool.max-idle:8}")
	private int maxIdle = 8;

	@Value("${redis.pool.min-idle:0}")
	private int minIdle = 0;

	@Value("${redis.pool.max-wait:-1}")
	private int maxWait = -1;

	// ==============================Methods==========================================
	@Primary
	@Bean(destroyMethod = "close")
	public JedisPool jedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWait);
		return new JedisPool(config, host, port, timeout, null, database, null);
	}

	@Primary
	@Bean
	public JedisTemplate jedisTemplate() {
		return new JedisTemplate(jedisPool());
	}

	@Primary
	@Bean
	public JedisUtils jedisUtils() {
		return new JedisUtils();
	}
}
