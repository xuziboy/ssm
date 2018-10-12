package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;

public class WechatAuthServiceTest extends BaseTest{
	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegister() {
		WechatAuth wechatAuth = new WechatAuth();
		Personinfo personinfo = new Personinfo();
		String openId = "dafadffadsfsda";
		personinfo.setCreateTime(new Date());
		personinfo.setName("测试一下");
		personinfo.setCustomerFlag(1);
		personinfo.setShopOwnerFlag(1);
		personinfo.setAdminFlag(1);
		wechatAuth.setPersoninfo(personinfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wae= wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wae.getState());
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		System.out.println(wechatAuth.getPersoninfo().getName());
	}

}
