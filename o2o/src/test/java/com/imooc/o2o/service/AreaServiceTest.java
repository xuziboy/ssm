package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	@SuppressWarnings("static-access")
	@Test
	public void tesGetAreaList() {
		List<Area>areaList = areaService.getAreaList();
		assertEquals(4,areaList.size());
		cacheService.removeFromCache(areaService.AREALISTKEY);
		areaList = areaService.getAreaList();
		
			


	}

}
