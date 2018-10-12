package com.imooc.o2o.service;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {
	
	/**
	 * 根据openId查找用户信息
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);
	
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;
}
