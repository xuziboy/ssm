package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{

	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisUtilKeys;
	@Autowired
	private JedisUtil.Strings jedisUtilStrings;

	Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);
	@Override
	@Transactional
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		String key = HEADLINELISTKEY;
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		//拼接redis的key
		if(headLineCondition!=null&&headLineCondition.getEnableStatus()!=null) {
			key = key+"_"+ headLineCondition.getEnableStatus();
		}
		if(!jedisUtilKeys.exists(key)) {
			headLineList = headLineDao.queryHeadLine(headLineCondition);
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(headLineList);
			}catch(Exception e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
			jedisUtilStrings.set(key, jsonString);
		}else {
			String jsonString = jedisUtilStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				headLineList = mapper.readValue(jsonString, javaType);
			}catch(Exception e) {
				logger.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}	
		}
		return headLineList;
	}

}
