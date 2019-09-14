package com.nrkey.lock.validators;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nrkey.lock.models.User;
import com.nrkey.lock.repositories.UserRepository;

@Component
public class UserValidator implements Validator {
	private final UserRepository userRepo;
	public UserValidator(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getConfirm().equals(user.getPassword())) {
        	System.out.println("Passwords don't match");
            errors.rejectValue("confirm", "Match");
        }
        if (userRepo.findByEmail(user.getEmail()) !=null ) {
            errors.rejectValue("email", "Taken");
        }
        if (userRepo.findByUsername(user.getUsername()) != null) {
        	errors.rejectValue("username", "Unique");
        }
    }
    
    public void authenticate(Object target, Errors errors) {
    	User login = (User) target;
    	User user = userRepo.findByEmail(login.getEmail());
    	if(user == null) {
    		errors.rejectValue("email", "NotFound");
    	}
    	if (!user.getIsActive()) {
        	System.out.println("Inactive account tried to log in");
        	errors.rejectValue("isActive","NotActive");
        }  else {
            // if the passwords match, return true, else, return false
            if(!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
                errors.rejectValue("password", "Incorrect");
            }
        }
    }
}