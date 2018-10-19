package com.imooc.o2o.service;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
	
	/**
	 * 根据用户uername和password获取localauth
	 * @param UserName
	 * @param Pwd
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName,String password); 
	
	/**
	 * 根据userId获取localAUth
	 * @param userId
	 */
	LocalAuth getLocalAuthByUerId(Long userId);
	
	/**
	 * 新增localAuth
	 * @param localAuth
	 */
	LocalAuthExecution BindLocalAuth(LocalAuth localAuth)throws LocalAuthOperationException;
	
	/**
	 * 更新localAuth
	 * @param userId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 */
	LocalAuthExecution modifyLocalAuth(Long userId,String userName,String password,
			String newPassword)throws LocalAuthOperationException;
	
}
