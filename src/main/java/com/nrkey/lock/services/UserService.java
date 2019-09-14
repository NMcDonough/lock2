package com.nrkey.lock.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.nrkey.lock.models.Permission;
import com.nrkey.lock.models.User;
import com.nrkey.lock.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository ur;
    
    public UserService(UserRepository userRepository) {
        this.ur = userRepository;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return ur.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return ur.findByEmail(email);
    }
    
    public User findByUsername(String username) {
    	return ur.findByUsername(username);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = ur.findById(id);
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    public List<User> getAllUsers() {
    	return this.ur.findAll();
    }
    
    public User updateUser(Long id, String email, String username, boolean isActive, Permission permission) {
    	Optional<User> user = ur.findById(id);
    	if(user.isPresent()) {
    		user.get().setUsername(username);
    		user.get().setEmail(email);
    		user.get().setId(id);
    		user.get().setIsActive(isActive);
    		user.get().setPermission(permission);
    		return ur.save(user.get());
    	} else {
    		return null;
    	}
    }
}
