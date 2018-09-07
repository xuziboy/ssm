package com.imooc.o2o.enums;

public enum ProductCategoryStateEnum {
	
	SUCCESS(1,"创建成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_LIST(-1002,"添加数少于1"),
	DELETE_SUCCESS(2,"删除成功"),
	DELETE_EMPTY(-1003,"删除数少于1");
	
	private int state;
	private String stateInfo;
	
	private ProductCategoryStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	/**
	 * 一局传入的state返回相应的enum的值
	 * 
	 */
	public static ProductCategoryStateEnum stateOf(int state) {
		for(ProductCategoryStateEnum productCategoryStateEnum:values()) {
			if(productCategoryStateEnum.getState()==state) {
				return productCategoryStateEnum;
			}
		}
		return null;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
}
