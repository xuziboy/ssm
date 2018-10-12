package com.imooc.o2o.service;

public interface CacheService {
	
	/**
	 * 根据key前缀删除匹配该模式下的所有key-value值
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);
}
