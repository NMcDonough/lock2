package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {
	
	public SiteController() {}
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		return "noah/index.jsp";
	}
}
