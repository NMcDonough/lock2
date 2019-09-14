package com.nrkey.lock.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nrkey.lock.models.Highscore;
import com.nrkey.lock.models.User;

@Repository
public interface HighscoreRepository extends CrudRepository<Highscore, Long> {
    Optional<Highscore> findById(Long id);
    List<Highscore> findAll();
    List<Highscore> findAllByUser(User user);
    Optional<Highscore> findByUserAndGame(User u, String game);
}