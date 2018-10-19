package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.util.MD5;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest{
	
	@Autowired
	private LocalAuthService localAuthService;
	
	@Test
	@Ignore
	public void testABindLocalAuth() {
		LocalAuth localAuth = new LocalAuth();
		Personinfo personinfo = new Personinfo();
		String userName = "testusername";
		String password = "123456";
		personinfo.setUserId(10L);
		localAuth.setPersoninfo(personinfo);
		localAuth.setUserName(userName);
		localAuth.setPassword(password);
		LocalAuthExecution lae = localAuthService.BindLocalAuth(localAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),lae.getState());
		localAuth = localAuthService.getLocalAuthByUerId(personinfo.getUserId());
		
		System.out.println("用户昵称:"+localAuth.getPersoninfo().getName());
		
	}
	@Test
	public void testBModifyLocalAuth() {
		long userId = 10;
		String userName = "testusername";
		String password = "123456";
		String newPassword = "654321";
		LocalAuthExecution lae = localAuthService.modifyLocalAuth(userId, userName, MD5.getMd5(password), MD5.getMd5(newPassword));
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),lae.getState());
	}
	
}
