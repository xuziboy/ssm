package com.imooc.o2o.interceptor.shopadmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.o2o.entity.Shop;

public class ShopPermissionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//从session中获取当前用户可操作的店铺列表
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
		if(currentShop!=null&&shopList!=null) {
			for(Shop shop:shopList) {
				if(shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
