package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		Personinfo owner = new Personinfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 3);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小："+shopList.size());
		System.out.println("店铺总数："+count);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 2);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小："+shopList.size());
		System.out.println("店铺总数："+count);
	}
	
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 13;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println("areaName:"+shop.getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		Personinfo owner = new Personinfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1,effectedNum);	
	}
	
	@Test
	@Ignore
	public void testUpdatetShop() {
		Shop shop = new Shop();
		shop.setShopId(13L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
		
	}

}
