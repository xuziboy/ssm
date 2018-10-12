package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.HeadLine;

public class HeadLineServiceTest extends BaseTest{
	
	@Autowired
	private HeadLineService headLineService;
	@Test
	public void testGetHeadLineList() {
		List<HeadLine> headLineList = null;
			try {
			headLineList = headLineService.getHeadLineList(new HeadLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(4,headLineList.size());
	}

}
