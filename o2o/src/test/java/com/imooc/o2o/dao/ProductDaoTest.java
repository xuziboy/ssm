package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testAInsertProduct()throws Exception{
		Shop shop1= new Shop();
		shop1.setShopId(13L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategotyId(13L);
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试desc");
		product1.setImgAddr("test1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试desc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(1);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		int effectedNum = productDao.insertProduct(product2);
		assertEquals(1,effectedNum);
		
	}
}
