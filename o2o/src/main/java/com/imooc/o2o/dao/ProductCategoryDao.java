package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 列出商铺种类列表
	 * @return areaList
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	
	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除商品类别
	 * @param productCategoryId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
	
	
}
