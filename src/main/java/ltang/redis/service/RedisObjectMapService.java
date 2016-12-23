package ltang.redis.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.stereotype.Service;

@Service
public class RedisObjectMapService {

	private Map<String, HashMapper<Object, String, String>> entityManagerMap = new HashMap<String, HashMapper<Object,String,String>>();
	
	@Autowired
	protected StringRedisTemplate stringRedisTemplate;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save(String key, Object obj, Class cls) {
		
		BoundHashOperations<String, String, String> ops = stringRedisTemplate
				.boundHashOps(key);
		if (!entityManagerMap.containsKey(obj.getClass().getName())) {
			entityManagerMap.put(obj.getClass().getName(),new DecoratingStringHashMapper<Object>(
					new JacksonHashMapper<Object>(cls)));
		}
		ops.putAll(entityManagerMap.get(obj.getClass().getName()).toHash(obj));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T get(String key, Class<T> cls) {
		Map<String, String> map = new DefaultRedisMap<String, String>(key,
				stringRedisTemplate);
		if (map != null && map.size() > 0) {
			if (entityManagerMap.get(cls.getName()) == null) {
				entityManagerMap.put(cls.getName(),new DecoratingStringHashMapper<Object>(
						new JacksonHashMapper<Object>((Class)cls)));
			}
			T t = (T) entityManagerMap.get(cls.getName()).fromHash(map);
			return t;
		}
		return null;
	}
	
	public void delete(String key){
		Set<String> keys = stringRedisTemplate.keys(key);
		if(!keys.isEmpty()){
			stringRedisTemplate.delete(keys);
		}
	}

	/**
	 * 获取对应字段值
	 * 
	 * @param fieldName
	 * @param keySeed
	 * @return
	 */
	public String getField(String key, String fieldName) {
		if (fieldName == null || "".equals(fieldName)) {
			return "";
		}
		BoundHashOperations<String, String, String> ops = stringRedisTemplate
				.boundHashOps(key);
		return ops.get(fieldName);
	}

	/**
	 * 更新具体字段值
	 * 
	 * @param fieldName
	 * @param newValue
	 * @param keySeed
	 */
	public void updateField(String key, String fieldName, Object newValue) {
		if (fieldName != null && !"".equals(fieldName)) {
			BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(key);
			ops.put(fieldName, newValue.toString());
		}
	}

	/**
	 * 删除字段(只有对存入对象为Map的时候有效的)
	 * 
	 * @param fieldName
	 * @param keySeed
	 */
	public void deleteField(String key, String fieldName) {
		if (fieldName != null && !"".equals(fieldName)) {
			BoundHashOperations<String, String, Object> ops = stringRedisTemplate
					.boundHashOps(key);
			ops.delete(fieldName);
		}
	}
	
	public long getNextNumById(String key) {
		return stringRedisTemplate.opsForHash().increment(key, "count", 1);
	}
	
	public void setExpireKey(String key, Date date) {
		stringRedisTemplate.expireAt(key, date);
	}
}
