package com.imooc.o2o.entity;

import java.util.Date;

//顾客消费的产品映射
public class UserProductMap {
	
	private Long userProductId;
	
	private Date createTime;
	
	//积分
	private Integer point;
	//产品
	private Product product;
	//顾客信息
	private Personinfo user;
	//商店
	private Shop shop;
	//操作员
	private Personinfo operator;
	public Long getUserProductId() {
		return userProductId;
	}
	public void setUserProductId(Long userProductId) {
		this.userProductId = userProductId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Personinfo getUser() {
		return user;
	}
	public void setUser(Personinfo user) {
		this.user = user;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Personinfo getOperator() {
		return operator;
	}
	public void setOperator(Personinfo operator) {
		this.operator = operator;
	}
	
	
}
