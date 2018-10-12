package com.imooc.o2o.service;

import com.imooc.o2o.entity.Personinfo;

public interface PersonInfoService {
	
	/**
	 * 根据用户id获取personinfo信息
	 * @param userId
	 * @return
	 */
	Personinfo getPersoninfoById(long userId);
	
}
