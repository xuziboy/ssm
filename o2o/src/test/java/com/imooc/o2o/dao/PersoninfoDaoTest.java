package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Personinfo;

public class PersoninfoDaoTest extends BaseTest {
	
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Test
	@Ignore
	public void testQueryPersonInfo() {
		Personinfo personInfo = personInfoDao.queryPersonInfoById(8);
		System.out.println(personInfo.getName());
	}
	@Test
	@Ignore
	public void testInsertPersoninfo() {
		Personinfo personInfo = new Personinfo();
		personInfo.setName("龙");
		personInfo.setCreateTime(new Date());
		personInfo.setEnableStatus(1);
		personInfo.setGender("2");
		personInfo.setLastEditTime(new Date());
		personInfo.setAdminFlag(1);
		personInfo.setCustomerFlag(1);
		personInfo.setShopOwnerFlag(1);
		int effectedNum = personInfoDao.insertPersonInfo(personInfo);
		assertEquals(1,effectedNum);
	}
	
	@Test
	public void testDetelePersonInfo() {
		int effectedNum = personInfoDao.deletePersonInfo(12);
		assertEquals(1,effectedNum);
	}
	
}
