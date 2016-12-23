package ltang.redis.template;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class DefaultObjectRedisTemplate extends RedisTemplate<String, Serializable> {

	@Override
	@Autowired
	public void setConnectionFactory(@Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory) {
		super.setConnectionFactory(connectionFactory);
	}
	
	@PostConstruct
	public void init(){
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		setKeySerializer(stringSerializer);
		setHashKeySerializer(stringSerializer);
	}
}
