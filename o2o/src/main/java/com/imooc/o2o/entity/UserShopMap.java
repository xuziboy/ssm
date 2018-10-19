package com.imooc.o2o.entity;

import java.util.Date;

//顾客店铺积分映射
public class UserShopMap {
	private Long userShopId;
	
	private Date createTime;
	
	//积分
	private Integer point;
	//顾客信息
	private Personinfo user;
	//店铺信息
	private Shop shop;
	public Long getUserShopId() {
		return userShopId;
	}
	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
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
	
	
}
