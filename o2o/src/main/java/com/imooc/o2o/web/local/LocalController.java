package com.imooc.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="local",method= {RequestMethod.GET})
public class LocalController {
	
	@RequestMapping(value="/bindauth")
	public String bindLocalAuth() {
		return "local/bindlocalauth";
	}
	@RequestMapping(value="/editpassword")
	public String editPassword() {
		return "local/editpassword";
	}
	@RequestMapping(value="/login")
	public String login() {
		return "local/login";
	}
	
}
