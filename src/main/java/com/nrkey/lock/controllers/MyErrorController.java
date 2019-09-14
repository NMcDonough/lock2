package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyErrorController implements ErrorController{

	public MyErrorController() {}
	
	@RequestMapping("/error")
	public String error(HttpSession session, Model model) {
		model.addAttribute("page", "error");
		return "lock/index.jsp";
	}
	
	@Override
    public String getErrorPath() {
        return "lock/error";
    }
}
