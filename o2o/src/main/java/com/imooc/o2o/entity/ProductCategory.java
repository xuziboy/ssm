package com.imooc.o2o.entity;

import java.util.Date;
//商品类别
public class ProductCategory {
	//Id
	private Long productCategotyId;
	//店铺Id
	private Long shopId;
	//商品类别名称
	private String productCategoryName;
	private Integer priority;
	private Date createTime;
	
	
	public Long getProductCategotyId() {
		return productCategotyId;
	}
	public void setProductCategotyId(Long productCategotyId) {
		this.productCategotyId = productCategotyId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
