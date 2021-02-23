package com.zxxa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zxxa.filter.MyHttpSessionListener;

@RestController
public class MyController {

	@RequestMapping("/hello")
	public String hello(HttpSession session){
		session.setAttribute("xxx", "ooo");
		return "Hello, springboot";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		return "login";
	}
	
	@RequestMapping("online")
	@ResponseBody
	public String online() {
		return "The current online people:" + MyHttpSessionListener.online;
	}
}
