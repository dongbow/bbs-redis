package ltang.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPoolConfig;

@Service("jedisPoolConfig")
public class DefaultJedisPoolConfig extends JedisPoolConfig {
	
	@Value("${redis.maxActive}")//最大连接数   
	public void setMaxActive(int maxActive) {
		super.setMaxTotal(maxActive);
	}

	@Override
	@Value("${redis.maxIdle}")//最大空闲数
	public void setMaxIdle(int maxIdle) {
		super.setMaxIdle(maxIdle);
	}

	@Override
	@Value("${redis.testOnBorrow}")//指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
	public void setTestOnBorrow(boolean testOnBorrow) {
		super.setTestOnBorrow(testOnBorrow);
	}
	@Value("${redis.maxWait}")//最大建立连接等待时间  
	public void setMaxWait(long maxWait) {
		super.setMaxWaitMillis(maxWait);
	}
	
	
	
}
