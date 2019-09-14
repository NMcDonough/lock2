package com.nrkey.lock.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrkey.lock.models.Permission;
import com.nrkey.lock.models.User;
import com.nrkey.lock.services.PermissionService;
import com.nrkey.lock.services.UserService;
import com.nrkey.lock.validators.UserValidator;

@RestController
@RequestMapping("api/user")
public class UserController {
	private UserValidator uv;
	private UserService us;
	private PermissionService ps;
	private String BASE_ROUTE = "lock/";
	
	public UserController(UserValidator uv, UserService us, PermissionService ps) {
		this.uv = uv;
		this.us = us;
		this.ps = ps;
	}
	
	@RequestMapping("/{id}")
	public String getUser(@PathVariable("id") Long id) {
		User user = this.us.findUserById(id);
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			json = "error";
		}
		System.out.println(json);
		System.out.println(user.getPermission());
		user.setPassword(null);
		return json;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public void register(HttpServletResponse response, @Valid @ModelAttribute("newUser") User user, Model model, HttpSession session, BindingResult result, RedirectAttributes redirectAttributes) {
		uv.validate(user, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser", user);
			model.addAttribute("user", new User());
			System.out.println("Errors found. Registration cancelled");
			for(FieldError fe : result.getFieldErrors()) {
				System.out.println(fe.getRejectedValue());
			}
			model.addAttribute("page", "log_reg");
			try {
				response.sendRedirect(BASE_ROUTE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Permission userPerm = ps.findByName("User");
		user.setPermission(userPerm);
		User u = us.registerUser(user);
		u.setPassword(null);
		session.setAttribute("user", u);
		
		try {
			response.sendRedirect(BASE_ROUTE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.PUT)
	public void editUser(HttpServletResponse res, @Valid @ModelAttribute("editUser") User user, @PathVariable("id") Long id) {
		Permission permission = ps.findById(id);
		user.setPermission(permission);
		us.updateUser(user.getId(), user.getEmail(), user.getUsername(), user.getIsActive(), user.getPermission());
	}
}
