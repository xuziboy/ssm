package com.imooc.o2o.entity;

import java.util.Date;

//顾客已经领取的奖品映射
public class UserAwardMap {
	
	private Long userAwardMapId;
	//创建时间
	private Date createTime;
	//使用状态 0:为兑换  1：已经兑换
	private Integer usedStatus;
	//积分
	private Integer point;
	//顾客信息
	private Personinfo user;
	//奖品
	private Award award;
	//店铺
	private Shop shop;
	//操作员信息
	private Personinfo operator;
	public Long getUserAwardMapId() {
		return userAwardMapId;
	}
	public void setUserAwardMapId(Long userAwardMapId) {
		this.userAwardMapId = userAwardMapId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUsedStatus() {
		return usedStatus;
	}
	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
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
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
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
