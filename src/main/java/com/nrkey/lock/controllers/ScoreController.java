package com.nrkey.lock.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrkey.lock.models.Highscore;
import com.nrkey.lock.models.ScoreParser;
import com.nrkey.lock.services.HighscoreService;
import com.nrkey.lock.services.UserService;

@RestController
@RequestMapping("/api/score")
public class ScoreController {
	private UserService us;
	private HighscoreService hs;
	
	public ScoreController(UserService us, HighscoreService hs) {
		this.hs = hs;
		this.us = us;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String updateScore(HttpServletRequest req) throws IOException {
		System.out.println(req.getParameter("data"));
		String json = req.getParameter("data");
		ScoreParser temp = new ObjectMapper().readValue(json, ScoreParser.class);
		Highscore oldScore = this.hs.getScore(us.findUserById(temp.user), temp.game);
		if (oldScore.getScore() < temp.score) {
			oldScore.setScore(temp.score);
			hs.saveScore(oldScore);
		}
	    return "hi";
	}
	
	@RequestMapping("/{userId}/{gameName}")
	public String returnScore(HttpServletRequest req, @PathVariable("userId") Long userId, @PathVariable("gameName") String game) throws JsonProcessingException {
		Highscore score = this.hs.getScore(us.findUserById(userId), game);
		System.out.println(score.getScore());
		return new ObjectMapper().writeValueAsString(score); 
	}
}