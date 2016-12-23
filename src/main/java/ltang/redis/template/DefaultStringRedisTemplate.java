package ltang.redis.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("stringRedisTemplate")
public class DefaultStringRedisTemplate extends StringRedisTemplate {
	@Override
	@Autowired
	public void setConnectionFactory(@Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory) {
		super.setConnectionFactory(connectionFactory);
	}
}
