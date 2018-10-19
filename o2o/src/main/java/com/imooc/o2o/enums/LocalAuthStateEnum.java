package com.imooc.o2o.enums;

public enum LocalAuthStateEnum {
	ADD_SUCCESS(1,"添加成功"),
	INNER_ERROR(-1001,"操作失败"),
	NULL_AUTH_INFO(-1002,"添加数少于1"),
	ONLY_ONE_ACCOUNT(3,"只能绑定一个账号"),
	MODIFY_SUCCESS(2,"更新成功");
	
	
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
	private LocalAuthStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
}
