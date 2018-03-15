/**
 * Jan 9, 2018
 */
package com.atguigu.shiro.handlers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 
 * @ClassName: ShiroHandler 
 * @Description: 
 * @author humin 
 * @date Jan 9, 2018 11:47:20 AM 
 *  
 */
//1. 获取当前的 Subject. 调用 SecurityUtils.getSubject();
//2. 测试当前的用户是否已经被认证. 即是否已经登录. 调用 Subject 的 isAuthenticated() 
//3. 若没有被认证, 则把用户名和密码封装为 UsernamePasswordToken 对象
//1). 创建一个表单页面
//2). 把请求提交到 SpringMVC 的 Handler
//3). 获取用户名和密码. 
//4. 执行登录: 调用 Subject 的 login(AuthenticationToken) 方法. 
//5. 自定义 Realm 的方法, 从数据库中获取对应的记录, 返回给 Shiro.
//1). 实际上需要继承 org.apache.shiro.realm.AuthenticatingRealm 类
//2). 实现 doGetAuthenticationInfo(AuthenticationToken) 方法. 
//6. 由 shiro 完成对密码的比对.
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password){
		
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){
			System.out.println("没有认证过！");
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				System.out.println("token1:" + token.hashCode());
				currentUser.login(token);
			} catch (AuthenticationException e) {
				System.out.println( "登陆失败：" + e.getMessage());
			}
			
		}
		return "redirect:/list.jsp";
	}
	
}
