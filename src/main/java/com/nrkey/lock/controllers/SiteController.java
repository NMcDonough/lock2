package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrkey.lock.models.User;
import com.nrkey.lock.services.UserService;

@Controller
public class SiteController {
	private UserService us;
	
	public SiteController(UserService us) {
		this.us = us;
	}
	
	@RequestMapping("/")
	public String home(HttpSession session, Model model) {
		System.out.println("Home route hit");
		if(session.getAttribute("user") != null) {
			Long userId = (Long) session.getAttribute("user");
			System.out.println("User found in session: " + userId);
			User user = us.findUserById(userId);
			user.setPassword(null);
			model.addAttribute("user", user);
		}
		return "index.jsp";
	}
	
	@RequestMapping("/login")
	public String logreg(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("user", new User());
		return "log_reg.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return "redirect:/";
	}
}
