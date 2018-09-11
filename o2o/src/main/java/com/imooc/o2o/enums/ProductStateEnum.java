package com.imooc.o2o.enums;

public enum ProductStateEnum {
	SUCCESS(1,"创建成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_LIST(-1002,"添加数少于1"),
	DELETE_SUCCESS(2,"删除成功"),
	DELETE_EMPTY(-1003,"删除数少于1");
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	/**
	 * 一局传入的state返回相应的enum的值
	 * 
	 */
	public static ProductStateEnum stateOf(int state) {
		for(ProductStateEnum productStateEnum:values()) {
			if(productStateEnum.getState()==state) {
				return productStateEnum;
			}
		}
		return null;
	}
}
