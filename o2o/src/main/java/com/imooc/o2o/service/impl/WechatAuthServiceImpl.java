package com.imooc.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dao.WechatAuthDao;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.exceptions.WechatAuthOperationException;
import com.imooc.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImpl implements WechatAuthService{

	private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
	@Autowired
	private WechatAuthDao wechatAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		if(wechatAuth ==null ||wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);		
		}
		try {
			//设置创建时间
			wechatAuth.setCreateTime(new Date());
			//如果微信账号里夹带着用户信息并且用户id为空  则认为该用户第一次使用平台( 且通过微信登录)
			//则自动创建用户信息
			if(wechatAuth.getPersoninfo()!=null&&wechatAuth.getPersoninfo().getUserId() ==null) {
				try {
					wechatAuth.getPersoninfo().setCreateTime(new Date());
					wechatAuth.getPersoninfo().setEnableStatus(1);
					Personinfo personinfo =wechatAuth.getPersoninfo();
					int effectedNum = personInfoDao.insertPersonInfo(personinfo);
					wechatAuth.setPersoninfo(personinfo);
					if(effectedNum <= 0) {
						throw new WechatAuthOperationException("添加用户信息失败");
					}
				}catch(Exception e) {
					log.error("insertPersonInfo error"+e.toString());
					throw new WechatAuthOperationException("insertPersonInfo error:"+e.getMessage());
				}
			}
			//创建专属于本平台的微信账号
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if(effectedNum <=0) {
				throw new WechatAuthOperationException("创建失败");
			}else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
			}
		}catch(Exception e) {
			log.error("insertWechatAuth error"+e.toString());
			throw new WechatAuthOperationException("insertWechatAuth error"+e.getMessage());
		}
	}
	

}
