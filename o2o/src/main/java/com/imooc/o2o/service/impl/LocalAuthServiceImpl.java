package com.imooc.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.LocalAuthDao;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOperationException;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.MD5;



@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalAuthByUserNameAndPwd(userName, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUerId(Long userId) {
		return localAuthDao.queryLocalAuthByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution BindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUserName()== null||localAuth.getPersoninfo()==null||localAuth.getPersoninfo().getUserId()==null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//查询此用户是否已绑定过平台账号
		LocalAuth tempAuth=localAuthDao.queryLocalAuthByUserId(localAuth.getPersoninfo().getUserId());
		if(tempAuth!=null) {
			//如果绑定过则直接退出
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			//如果之前没有绑定过平台账号,则创建一个平台账号与该用户绑定
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			//判断创建是否成功
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.ADD_SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("insertLocalAuth error: "
					+ e.getMessage());
		}	
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword
			) throws LocalAuthOperationException {
		//非空判断,判断传入的用户Id,新旧密码是否为空
		if(userId!=null&&userName!=null&&password!=null&&newPassword!=null&&!password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
				if(effectedNum<0) {
					return new LocalAuthExecution(LocalAuthStateEnum.INNER_ERROR);
				}
			}catch(Exception e) {
				throw new LocalAuthOperationException("更新失败"+e.toString());
			}
		}else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		return new LocalAuthExecution(LocalAuthStateEnum.ADD_SUCCESS);
	}
	

}
