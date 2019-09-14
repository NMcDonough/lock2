package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrkey.lock.models.User;
import com.nrkey.lock.services.UserService;

@Controller
@RequestMapping("/games")
public class GameController {
	private UserService us;
	
	public GameController(UserService us) {
		this.us = us;
	}
	
	@RequestMapping("/phasergame")
	public String phasergame(HttpSession session, Model model) {
		model.addAttribute("page", "games/phasergame");
		model.addAttribute("game", "phaserGame");
		if(session.getAttribute("user") != null) {
			Long userId = (Long) session.getAttribute("user");
			User user = us.findUserById(userId);
			user.setPassword(null);
			model.addAttribute("user", user);
		}
		return "lock/index.jsp";
	}
}
