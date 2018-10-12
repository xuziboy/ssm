package com.imooc.o2o.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.cache.JedisUtil;

public class RemoveRedis {
	@Autowired
	private JedisUtil.Keys jedisUtilKeys;
	
	@Test
	public void Remove() {
		long num = jedisUtilKeys.del("headLineList");
		System.out.println(num);
		
	}
}
