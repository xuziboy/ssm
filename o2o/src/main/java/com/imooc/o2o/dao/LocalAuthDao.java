package com.imooc.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.LocalAuth;

public interface LocalAuthDao {
	
	/**
	 * 根据userName和password查询
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalAuthByUserNameAndPwd(@Param("userName")String userName,@Param("password")String password);
	
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalAuthByUserId(@Param("userId")Long userId);
	
	/**
	 * 插入
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	
	/**
	 * 更新用户信息
	 * @param userId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId")Long userId,@Param("userName")String userName,@Param("password")String password,@Param("newPassword")String newPassword
			,@Param("lastEditTime")Date lastEditTime);
}
