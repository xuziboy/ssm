package com.imooc.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.Personinfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value="local",method= {RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
	
	@Autowired
	private LocalAuthService localAuthService;
	
	@RequestMapping(value="/bindlocalauth",method= {RequestMethod.POST})
	@ResponseBody
	/**
	 * 绑定本地账号
	 * @param request
	 * @return
	 */
	private Map<String,Object> bindLocalAuth(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证校验码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		Personinfo user = (Personinfo)request.getSession().getAttribute("user");
		if(userName!=null&&password!=null&&user!=null&&user.getUserId()!=null) {
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersoninfo(user);
			LocalAuthExecution lae = localAuthService.BindLocalAuth(localAuth);
			if(lae.getState() == LocalAuthStateEnum.ADD_SUCCESS.getState()) {
				modelMap.put("success",true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", lae.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/changelocalpwd",method=RequestMethod.POST)
	@ResponseBody
	/**
	 * 修改用户密码
	 * @param request
	 * @return
	 */
	private Map<String,Object> changeLocalPwd(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证校验码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		Personinfo user = (Personinfo)request.getSession().getAttribute("user");
		if(userName!=null&&password!=null&&user!=null&&user.getUserId()!=null&&!password.equals(newPassword)) {
			try {
				LocalAuth localAuth = localAuthService.getLocalAuthByUerId(user.getUserId());
				if(localAuth==null||!localAuth.getUserName().equals(userName)) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的账号非本次登录的账号");
					return modelMap;
				}
				LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
				if(le.getState() == LocalAuthStateEnum.ADD_SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/logincheck",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logincheck(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//获取是否需要进行验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		//验证校验码
		if(needVerify&&!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		if(userName!=null&&password!=null) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if(localAuth!=null) {
				modelMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersoninfo());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和账号不能为空");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logout(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//将用户session志为空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
