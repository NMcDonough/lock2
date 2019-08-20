package com.nrkey.lock.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nrkey.lock.models.User;
import com.nrkey.lock.services.UserService;

@Controller
public class SiteController {
	private UserService us;
	
	public SiteController(UserService us) {
		this.us = us;
	}
	
	@RequestMapping("/")
	public String home(HttpSession session) {
		return "noah/index.jsp";
	}
	
	@RequestMapping(value = "/resume", method = RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile()
	        throws IOException {

	    ClassPathResource pdfFile = new ClassPathResource("noah_mcdonough.pdf");

	    return ResponseEntity
	            .ok()
	            .header("Content-Disposition", "attachment; filename=noah_mcdonough.pdf")
	            .contentLength(pdfFile.contentLength())
	            .contentType(
	                    MediaType.APPLICATION_PDF.parseMediaType("application/pdf"))
	            .body(new InputStreamResource(pdfFile.getInputStream()));
	}
	
	@RequestMapping("/lock")
	public String lock(HttpSession session, Model model) {
		System.out.println("Home route hit");
		if(session.getAttribute("user") != null) {
			Long userId = (Long) session.getAttribute("user");
			System.out.println("User found in session: " + userId);
			User user = us.findUserById(userId);
			user.setPassword(null);
			System.out.println(user.getUsername());
			model.addAttribute("user", user);
		}
		return "index.jsp";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
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
		return "about.jsp";
	}
}
