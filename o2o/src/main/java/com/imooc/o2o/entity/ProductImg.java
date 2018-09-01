package com.imooc.o2o.entity;

import java.util.Date;
//产品图片
public class ProductImg {
	private Long productimgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;
	public Long getProductimgId() {
		return productimgId;
	}
	public void setProductimgId(Long productimgId) {
		this.productimgId = productimgId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
