package com.it.j2ee.modules.permission.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.it.j2ee.modules.permission.entity.User;
import com.it.j2ee.modules.permission.service.MenuService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private MenuService menuService;

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "account/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(HttpServletRequest request, HttpServletResponse response,@ModelAttribute User user,
			BindingResult errors) {
		// 先校验验证码
		
		
		String message = "";
		// 调用shiro进行登陆验证
		Object loginUser = user;
		UsernamePasswordToken userToken=null;
		if(null!=loginUser){
			try {
				// 页面传参，根据用户名、密码初始化token
				userToken = new UsernamePasswordToken(user.getUserName(), "aa");
				// 调用login方法后
				// SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
				Subject curUser = SecurityUtils.getSubject();
				curUser.login(userToken);
				
			} catch (AuthenticationException e) {
				userToken.clear();
				errors.reject("error.login", "登录出错!");
			}
		}else{
			errors.reject("error.login", StringUtils.isBlank(message)?"用户名或密码错误!!":message);
		}
		
		if (errors.hasErrors()) {
			return "account/login";
		} else {
			return "redirect:/";
		}
	}
}
