package ltang.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPoolConfig;

@Service("jedisConnectionFactory")
public class DefaultJedisConnectionFactory extends JedisConnectionFactory {

	@Override
	@Autowired
	public void setPoolConfig(
			@Qualifier("jedisPoolConfig") JedisPoolConfig poolConfig) {
		super.setPoolConfig(poolConfig);
	}

	@Override
	@Value("${redis.host}")
	public void setHostName(String hostName) {
		super.setHostName(hostName);
	}

	@Override
	@Value("${redis.pass}")
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	@Value("${redis.port}")
	public void setPort(int port) {
		super.setPort(port);
	}

	@Override
	@Value("${redis.timeout}")
	public void setTimeout(int timeout) {
		super.setTimeout(timeout);
	}
}
