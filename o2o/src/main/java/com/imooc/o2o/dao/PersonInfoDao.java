package com.imooc.o2o.dao;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Personinfo;

public interface PersonInfoDao {
	
	/**
	 * 通过用户Id查询用户
	 * @param userId
	 * @return
	 */
	Personinfo queryPersonInfoById(long userId);
	
	/**
	 * 添加用户信息
	 * @param personinfo
	 * @return
	 */
	int insertPersonInfo(Personinfo personinfo);
	
	/**
	 * 查询多个用户信息
	 * @param personInfoCondition
	 * @return
	 */
	Personinfo queryPersonInfoList(@Param("personInfoCondition")Personinfo personInfoCondition);
	
	/**
	 * 查询用户数量
	 * @param personInfoCondition
	 * @return
	 */
	int queryPersonInfoCount(@Param("personInfoCondition")Personinfo personInfoCondition);
	
	/**
	 * 更新用户信息
	 * @param personInfo
	 * @return
	 */
	int updatePersonInfo(Personinfo personInfo);
	
	/**
	 * 根据用户Id删除信息
	 * @param userId
	 * @return
	 */
	int deletePersonInfo(long userId);
}
