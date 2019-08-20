package com.nrkey.lock.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {
	public GameController() {}
	
	@RequestMapping("/phasergame")
	public String phasergame(HttpSession session, Model model) {
		return "phasergame.jsp";
	}
}
