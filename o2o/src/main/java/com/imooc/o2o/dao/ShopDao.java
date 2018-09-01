package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 通过shop id 查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(Long shopId);
	/*
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/*
	 * 更新店铺信息
	 */
	int updateShop(Shop shop);
}
