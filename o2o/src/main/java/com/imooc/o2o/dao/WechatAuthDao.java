package com.imooc.o2o.dao;

import com.imooc.o2o.entity.WechatAuth;

public interface WechatAuthDao {
	/**
	 * 通过openid查询对心本平台的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuth queryWechatInfoByOpenId(String openId);
	
	/**
	 * 添加对应本平台的微信账号
	 * @param wechatAuth
	 * @return
	 */
	int insertWechatAuth(WechatAuth wechatAuth);
	
	/**
	  *   通过wechatAuthId删除信息
	 * @param wechatAuthId
	 * @return
	 */
	int deleteWechatAuth(long wechatAuthId);
}	
