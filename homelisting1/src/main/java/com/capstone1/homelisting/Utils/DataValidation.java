package com.capstone1.homelisting.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.capstone1.homelisting.model.Users;
import com.capstone1.homelisting.repository.UserRepository;


@Component
public class DataValidation implements Validator {

@Autowired
public UserRepository userRepository;

String emailRegex ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

String passwordRegex ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


//String phoneRegex ="\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

public boolean supports(Class<?> clazz) {
return Users.class.equals(clazz);
}

public void validate(Object o, Errors errors) {
Users user = (Users) o;
ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "size.user.firstName");
ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "size.user.lastName");


ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
if (userRepository.getByEmail(user.getEmail()).isPresent()) {
    errors.rejectValue("email", "size.user.unique");
    }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
       
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2", "NotEmpty");
        if (!user.getPassword2().equals(user.getPassword())) {
    errors.rejectValue("password2", "match.user.password2");
    }
        
        if (!user.getEmail().matches(emailRegex)) {
        errors.rejectValue("email", "size.user.email");
    }

       if(!user.getEmail().matches(passwordRegex)) { 
       errors.rejectValue("password","size.user.password"); 
}
        
/*
* if (!user.getPhone().matches(phoneRegex)) { errors.rejectValue("phone",
* "size.user.phone"); }
*/

}
}