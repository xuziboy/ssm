package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.entity.WechatAuth;

public class WechatAuthDaoTest extends BaseTest{
	
	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Test
	@Ignore
	public void testQueryWechatAuthDao() {	
		WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("ovLbns-gxJHqC-UTPQKvgEuENl-E");
		System.out.println(wechatAuth);
	}
	@Test
	@Ignore
	public void testInsertWechatAuth() {
		WechatAuth wechatAuth = new WechatAuth();
		Personinfo personinfo = new Personinfo();
		personinfo.setUserId(8L);
		wechatAuth.setOpenId("8");
		wechatAuth.setPersoninfo(personinfo);
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1,effectedNum);
	}
	@Test
	public void testDeleteWechatAuth() {
		int effectedNum = wechatAuthDao.deleteWechatAuth(8);
		assertEquals(1,effectedNum);
	}
}
