package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productServcie;
	
	@Test
	public void testAddProduct() throws ShopOperationException,FileNotFoundException{
		//创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(13L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategotyId(11L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		//创建缩略图文件流
		File thumbnailFile = new File("E:/图片/车.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
		//创建两个商品详情图文件流并将它们添加到详情图片列表中
		File productImg1 = new File("E:/图片/熊猫.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("E:/图片/星.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(),is1));
		productImgList.add(new ImageHolder(productImg2.getName(),is2));
		//添加商品并验证
		ProductExecution pe = productServcie.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
	}
}
