package ltang.redis.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.SessionCallback;

@Service
public class RedisObjectZSetService {

	@Autowired
	protected StringRedisTemplate redisTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean addItem(final String key, final Object targetId,
			final long weight) {
		return redisTemplate.execute((new SessionCallback<Boolean>() {

			public Boolean execute(RedisOperations operations)
					throws DataAccessException {
				operations.multi();
				BoundZSetOperations<String, Object> zsetOperations = operations
						.boundZSetOps(key);
				
				zsetOperations.add(targetId, weight);

				operations.exec();
				return true;
			}
		}));
	}
	
	public Set<String> getZsetRangeByWeight(String key, long minWeight, long mxWeight){
		BoundZSetOperations<String, String> zset = redisTemplate.boundZSetOps(key);
		return zset.rangeByScore(minWeight, mxWeight);
	}
	
	public Set<ZSetOperations.TypedTuple<String>> getZsetRangeByWeightWitScore(String key, long minWeight, long mxWeight){
		BoundZSetOperations<String, String> zset = redisTemplate.boundZSetOps(key);
		return zset.rangeByScoreWithScores(minWeight,mxWeight);
	}
	
	
}
