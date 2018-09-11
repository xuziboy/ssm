package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 0, 2);
		System.out.println("店铺列表："+se.getShopList().size());
		System.out.println("店铺总数："+se.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(55L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("E:/图片/89.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("女孩.jpg",is);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片的地址:"+shopExecution.getShop().getShopImg());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试的店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("E:/图片/89.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("女孩.jpg",is);
		ShopExecution se = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
	}

}
