package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nrkey.lock.models.User;
import com.nrkey.lock.services.UserService;
import com.nrkey.lock.validators.UserValidator;

@Controller
public class UserController {
	private UserValidator uv;
	private UserService us;
	
	public UserController(UserValidator uv, UserService us) {
		this.uv = uv;
		this.us = us;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("newUser") User user, Model model, HttpSession session, BindingResult result, RedirectAttributes redirectAttributes) {
		uv.validate(user, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser", user);
			model.addAttribute("user", new User());
			System.out.println("Errors found. Registration cancelled");
			for(FieldError fe : result.getFieldErrors()) {
				System.out.println(fe.getRejectedValue());
			}
			return "log_reg.jsp";
		}
		User u = us.registerUser(user);
		u.setPassword(null);
		session.setAttribute("user", u.getId());
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, Model model, @ModelAttribute("user") User user) {
		if(us.authenticateUser(user.getEmail(), user.getPassword())) {
			user = us.findByEmail(user.getEmail());
			Long id = user.getId();
			model.addAttribute("user", id);
			session.setAttribute("user", id);
			System.out.println("Authentication successful.");
			return "redirect:/";
		} else {
			model.addAttribute("loginError", true);
			model.addAttribute("newUser", new User());
			user.setPassword(null);
			model.addAttribute("user", user);
			return "log_reg.jsp";
		}
	}
}
