package com.nrkey.lock.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nrkey.lock.models.Permission;
import com.nrkey.lock.models.User;
import com.nrkey.lock.services.HighscoreService;
import com.nrkey.lock.services.PermissionService;
import com.nrkey.lock.services.UserService;
import com.nrkey.lock.validators.UserValidator;

@Controller
@RequestMapping("/lock")
public class LockController {
	private UserService us;
	private String BASE_ROUTE = "lock/";
	private PermissionService ps;
	private UserValidator uv;
	private HighscoreService hs;
	
	public LockController(UserService us, PermissionService ps, UserValidator uv, HighscoreService hs) {
		this.us = us;
		this.ps = ps;
		this.uv = uv;
		this.hs = hs;
	}
	
	@RequestMapping("")
	public String locker(HttpSession session, Model model) {
		if(session.getAttribute("user") != null) {
			Long userId = (Long) session.getAttribute("user");
			User user = us.findUserById(userId);
			user.setPassword(null);
			model.addAttribute("user", user);
		} else {
			model.addAttribute("user", null);
		}
		model.addAttribute("page", "home");
		return BASE_ROUTE + "index.jsp";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String logreg(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("user", new User());
		model.addAttribute("page", "log_reg");
		model.addAttribute("loginError", false);
		return BASE_ROUTE + "index.jsp";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletResponse res, HttpSession session, Model model, @Valid @ModelAttribute("user") User user, BindingResult result) {
		uv.authenticate(user, result);
		if(!result.hasErrors()) {
			user = us.findByEmail(user.getEmail());
			Long id = user.getId();
			session.setAttribute("user", id);
			model.addAttribute("user", user);
			System.out.println("Authentication successful.");
			model.addAttribute("page", "home");
			
		} else {
			model.addAttribute("newUser", new User());
			user.setPassword(null);
			model.addAttribute("user", user);
			model.addAttribute("page","log_reg");
		}
		return BASE_ROUTE + "index.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return "redirect:../" + BASE_ROUTE;
	}
	
	@RequestMapping("/about")
	public String about(HttpSession session, Model model) {
		if(session.getAttribute("user") != null) {
			Long userId = (Long) session.getAttribute("user");
			System.out.println("User found in session: " + userId);
			User user = us.findUserById(userId);
			user.setPassword(null);
			System.out.println(user.getUsername());
			model.addAttribute("user", user);
		}
		model.addAttribute("page","about");
		return BASE_ROUTE + "index.jsp";
	}
	
	@RequestMapping("/admin")
	public String adminTools(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			return "redirect:" + BASE_ROUTE;
		} else {
			model.addAttribute("page", "adminTools");
			User u = us.findUserById((Long) session.getAttribute("user"));
			u.setPassword(null);
			model.addAttribute("user", u);
			model.addAttribute("newPermission", new Permission());
			model.addAttribute("permissions", this.ps.allPermissions());
			model.addAttribute("editPermission", new Permission());
			model.addAttribute("users", us.getAllUsers());
			model.addAttribute("editUser", new User());
			return BASE_ROUTE + "index.jsp";
		}
	}
	
	@RequestMapping("user/{username}")
	public String profile(HttpSession session, Model model, @PathVariable("username") String username) {
		User user = us.findByUsername(username);
		if(user == null) {
			return "redirect:/";
		} else {
			user.setPassword(null);
			model.addAttribute("userProfile", user);
			Long userId = (Long) session.getAttribute("user");
			System.out.println("User found in session: " + userId);
			if(session.getAttribute("user") != null) {
				model.addAttribute("user", us.findUserById(userId));
			}
			System.out.println(this.hs.findUserScores(user).size());
			model.addAttribute("scores", this.hs.findUserScores(user));
			model.addAttribute("page","profile");
			return BASE_ROUTE + "index.jsp";
		}
	}
}
