package com.nrkey.lock.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nrkey.lock.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email); 
    User findByUsername(String username);
}