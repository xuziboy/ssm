package com.imooc.o2o.util;

public class PageCalculator {
	public static int calculateRowIndex(int pageIndex,int pageSize) {
		return (pageIndex > 0?(pageSize -1)*pageSize:0);
	}

}
