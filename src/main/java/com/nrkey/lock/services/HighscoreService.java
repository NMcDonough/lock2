package com.nrkey.lock.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nrkey.lock.models.Highscore;
import com.nrkey.lock.models.User;
import com.nrkey.lock.repositories.HighscoreRepository;

@Service
public class HighscoreService {
	private HighscoreRepository hr;
	
	public HighscoreService(HighscoreRepository hr) {
		this.hr = hr;
	}
	
	public Highscore saveScore(Highscore score) {
		return this.hr.save(score);
	}
	
	public Highscore getScore(User u, String game) {
		Optional<Highscore> score = this.hr.findByUserAndGame(u, game);
		System.out.println(score);
		try {
			return score.get();
		} catch(NoSuchElementException e) {
			return new Highscore(u, game, 0);
		}
	}
	
	public List<Highscore> findUserScores(User user) {
		return hr.findAllByUser(user);
	}
}