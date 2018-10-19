package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.util.MD5;

public class LocalAuthDaoTest extends BaseTest{
	
	@Autowired
	private LocalAuthDao localAuthDao;
	
	@Test
	@Ignore
	public void testInsertLocalAuth() {
		LocalAuth localAuth = new LocalAuth();
		Personinfo personinfo = new Personinfo();
		personinfo.setUserId(19L);
		localAuth.setUserName("xuziboy");
		localAuth.setPassword(MD5.getMd5("123456"));
		localAuth.setPersoninfo(personinfo);
		localAuth.setCreateTime(new Date());
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1,effectedNum);
	}
	@Test
	@Ignore
	public void testQueryLocalAuthByUserNameAndPwd() {
		LocalAuth localAuth = localAuthDao.queryLocalAuthByUserNameAndPwd("xuziboy", MD5.getMd5("123456"));
		System.out.print(localAuth.getLocalAuthId());
	}
	
	@Test
	@Ignore
	public void testQueryLocalAuthByUserId() {
		LocalAuth localAuth = localAuthDao.queryLocalAuthByUserId(19L);
		System.out.println(localAuth.getLocalAuthId());
	}
	@Test
	public void testUpdateLocalAuth() {
		Long userId =19L;
		String userName="xuziboy";
		String password = MD5.getMd5("123456");
		String newPassword = MD5.getMd5("654321");
		Date lastEditTime = new Date();
		int effectedNum = localAuthDao.updateLocalAuth(userId, userName, password, newPassword, lastEditTime);
		assertEquals(1,effectedNum);
	}
}
