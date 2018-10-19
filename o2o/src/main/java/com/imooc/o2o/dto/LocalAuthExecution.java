package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {
	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//商品数量
	private int count;
	
	//操作的product(增删改查的时候使用)
	private LocalAuth localAuth;
	
	//获取的product列表(查询商品列表的时候用)
	private List<LocalAuth> localAuthList;
	
	public LocalAuthExecution() {
		
	}
	
	//失败的时候使用的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalAuth getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}

	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}

	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}

	//成功的时候用的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuth = localAuth;
	}
}
