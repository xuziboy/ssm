package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	@Ignore
	public void testABatchInsertProductImg()throws Exception{
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effected = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2,effected);
	}
	
	@Test
	@Ignore
	public void testBQueryProductImgList() {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
		assertEquals(2,productImgList.size());
	}
	
	@Test
	public void testCDeleteProductImgByProductId() throws Exception{
		//删除新增的两条图片
		int effectedNum=productImgDao.deleteProductImgByProductId(8L);
		assertEquals(2,effectedNum);
	}
}

