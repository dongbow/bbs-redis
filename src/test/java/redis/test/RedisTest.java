package redis.test;

import javax.annotation.Resource;

import ltang.redis.service.RedisObjectListService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-context-test.xml"})
public class RedisTest {

	@Resource
	private RedisObjectListService service;
	
	@Test
	public void test() {
		System.out.println(service);
	}
	
}
