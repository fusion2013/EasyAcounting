package com.fusion.ea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login() {
		System.out.println("login...");
		return "login";
	}
	
	@RequestMapping("/dashboard")
	public String homePageRequest() {
		System.out.println("homePageRequest...");
		return "dashboard";
	}
	
	@RequestMapping("/validateLogin")
	public String validateLogin() {
		System.out.println("Validate...");
		return "dashboard";
	}
	
}
