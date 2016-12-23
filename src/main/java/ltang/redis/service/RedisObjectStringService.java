package ltang.redis.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisObjectStringService {
	
	@Autowired
	protected StringRedisTemplate stringRedisTemplate;
	
	public void save(String key, String value) {
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(key);
		ops.set(value);
	}
	
	public String get(String key) {
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(key);
		return ops.get();
	}
	
	public void setExpireKey(String key, Date date) {
		stringRedisTemplate.expireAt(key, date);
	}
}
