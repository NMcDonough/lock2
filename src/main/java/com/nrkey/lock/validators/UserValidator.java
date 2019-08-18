package com.nrkey.lock.validators;

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
            errors.rejectValue("confirm", "Match");
        }
        if (userRepo.findByEmail(user.getEmail()) !=null ) {
            errors.rejectValue("email", "Taken");
        }
        if (userRepo.findByUsername(user.getUsername()) != null) {
        	errors.rejectValue("username", "Unique");
        }
    }
    
    
}