package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryOperationException;
import com.imooc.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Autowired
	private JedisUtil.Keys jedisUtilKeys;
	@Autowired
	private JedisUtil.Strings jedisUtilStrings;
	

	Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
	
	@Override
	@Transactional
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key = SHOPCATEGORYLISTKE;
		List<ShopCategory> shopCategoryList = null;
		ObjectMapper mapper = new ObjectMapper();
		//拼接key
		if(shopCategoryCondition==null) {
			//查询条件为空，则列出所有首页大类， 即parentId为空的店铺类别
			key = key +"_allfirstlevel";
		}else if(shopCategoryCondition!=null&&shopCategoryCondition.getParent()!=null
				&&shopCategoryCondition.getParent().getShopCategoryId()!=null) {
			//若parent不为空 ，则列出parentId的所有子类
			key = key + "_parent"+shopCategoryCondition.getParent().getShopCategoryId();
		}else if(shopCategoryCondition!=null) {
			//列出所有子类别,不管属于哪个类，都列出来
			key = key+"_allsecondlevel";
		}
		if(!jedisUtilKeys.exists(key)) {
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String jsonString =null;	
			try {
				jsonString = mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			}
			jedisUtilStrings.set(key, jsonString);
		}else {
			String jsonString = jedisUtilStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			}
		}
		return shopCategoryList;
	}
	

}
