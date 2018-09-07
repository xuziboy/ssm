package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	@Ignore
	public void testQueryByShopId() throws Exception{
		long shopId = 13;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("该店铺自定义的类别树为：" + productCategoryList.size());
	}
	
	@Test
	@Ignore
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(13L);
		
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(13L);
		
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectnum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2,effectnum);
		
	}
	
	@Test 
	public void testDeleteProductCategory() {
		long productCategoryId = 5L;
		int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,13L);
		assertEquals(1,effectedNum);
	}

}
