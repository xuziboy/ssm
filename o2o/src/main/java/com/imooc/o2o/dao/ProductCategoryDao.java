package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 列出商铺种类列表
	 * @return areaList
	 */
	List<ProductCategory> queryProductCategory();
}
