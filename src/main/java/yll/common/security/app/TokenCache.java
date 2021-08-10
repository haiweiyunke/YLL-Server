package yll.common.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import yll.common.identifier.IdHelper;
import yll.common.jedis.JedisCallback;
import yll.common.jedis.JedisTemplate;
import yll.service.model.YllConstants;

@Component
public class TokenCache {
	// ==============================Fields===========================================
	/** 用户信息 */
	public static final String REDIS_PREFIX = "YLL:USERTOKEN";

	@Autowired
	private JedisTemplate jedisTemplate;

	@Value("${user-token.timeout.seconds}")
	private Integer sessionTimeoutSeconds = 12 * 60 * 60;

	@Value("${phone-code-token.timeout.seconds}")
	private Integer codeTimeoutSeconds = 2 * 60;

	// ==============================Methods==========================================
	public String get(String token) {
		final String key = getKey(token);
		return key == null ? null : jedisTemplate.execute(new JedisCallback<String>() {
			@Override
			public String execute(Jedis jedis) {
				jedis.expire(key, sessionTimeoutSeconds);
				return jedis.get(key);
			}
		});
	}

	public String put(final String userId) {
		String token = IdHelper.nextId();
		final String key = getKey(token);
		jedisTemplate.execute(new JedisCallback<Boolean>() {
			@Override
			public Boolean execute(Jedis jedis) {
				jedis.setex(key, sessionTimeoutSeconds, userId);
				return Boolean.TRUE;
			}
		});
		return token;
	}

	public void remove(String token) {
		final String key = getKey(token);
		if (key != null) {
			jedisTemplate.execute(new JedisCallback<Boolean>() {
				@Override
				public Boolean execute(Jedis jedis) {
					jedis.del(key);
					return Boolean.TRUE;
				}
			});
		}
	}

	public boolean check(String token) {
		final String key = getKey(token);
		return key == null ? false : jedisTemplate.execute(new JedisCallback<Boolean>() {
			@Override
			public Boolean execute(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	private String getKey(String sessionId) {
		return sessionId == null ? null : (REDIS_PREFIX + sessionId);
	}

	/**
	 * 短信验证码
	 * @param code
	 * @return
	 */
	public String putCode(final String code, String username) {
		String token = IdHelper.nextId();
		final String key = getCodeKey(token);
		jedisTemplate.execute(new JedisCallback<Boolean>() {
			@Override
			public Boolean execute(Jedis jedis) {
				jedis.setex(key, codeTimeoutSeconds, username.concat("_").concat(code));
				return Boolean.TRUE;
			}
		});
		return token;
	}

	/**
	 * 短信验证码使用
	 * @param
	 * @return
	 */
	public String getCode(String token) {
		final String key = getCodeKey(token);
		return key == null ? null : jedisTemplate.execute(new JedisCallback<String>() {
			@Override
			public String execute(Jedis jedis) {
				jedis.expire(key, sessionTimeoutSeconds);
				return jedis.get(key);
			}
		});
	}

	public void removeSMS(String token) {
		final String key = getCodeKey(token);
		if (key != null) {
			jedisTemplate.execute(new JedisCallback<Boolean>() {
				@Override
				public Boolean execute(Jedis jedis) {
					jedis.del(key);
					return Boolean.TRUE;
				}
			});
		}
	}

	/**
	 * 短信验证码使用
	 * @param sessionId
	 * @return
	 */
	private String getCodeKey(String sessionId) {
		return sessionId == null ? null : (YllConstants.REG_VERIFYCODE  + "_"  + sessionId);
	}
}
