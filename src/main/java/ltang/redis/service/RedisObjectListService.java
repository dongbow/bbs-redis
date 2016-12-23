package ltang.redis.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import ltang.redis.template.DefaultObjectRedisTemplate;

import org.springframework.stereotype.Service;

@Service
public class RedisObjectListService {
	
	@Resource
	private DefaultObjectRedisTemplate redisTemplate;
	
	/**
	 * 添加到第一个位置
	 * @param key
	 * @param value
	 */
	public void preAppend(String key,Serializable value){
		redisTemplate.boundListOps(key).leftPush(value);
	}
	
	/**
	 * 添加到最后一个位置
	 * @param key
	 * @param value
	 */
	public void append(String key,Serializable value){
		redisTemplate.boundListOps(key).rightPush(value);
	}
	
	/**
	 * 移除最后一个
	 * @param key
	 * @return
	 */
	public Object removeLast(String key){
		return redisTemplate.boundListOps(key).rightPop();
	}
	
	/**
	 * 移除最后一个
	 * @param key
	 * @return
	 */
	public Object removeFirst(String key){
		return redisTemplate.boundListOps(key).leftPop();
	}
	
	/**
	 * 获取列表中的部分数据
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Serializable> getRange(String key,long start,long end){
		 return redisTemplate.boundListOps(key).range(start, end);
	}
	
	/**
	 * 移除整个Key的缓存列表
	 * @param key
	 */
	public void remove(String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 返回列表长度
	 * @param key
	 * @return
	 */
	public long getListLength(String key) {
		return redisTemplate.boundListOps(key).size();
	}
	
	/**
	 * 查询列表中的一个
	 * @param key
	 * @return
	 */
	public Serializable getObjectByIndex(String key, long index) {
		return redisTemplate.boundListOps(key).index(index);
	}
}
