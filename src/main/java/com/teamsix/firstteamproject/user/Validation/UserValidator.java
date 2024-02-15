package com.teamsix.firstteamproject.user.Validation;

import com.teamsix.firstteamproject.user.Entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        //username
        if(user.getName().length() > 10 || user.getName().length() <= 0){
            errors.rejectValue("username", "length");
        }

        //pasword
        if(user.getPw().length() > 20 || user.getPw().length()  < 6){
            errors.rejectValue("password", "length");
        }

        //TODO
        //email

        //전체 검증
        if(user.getName() == ""  || user.getPw() == "" || user.getEmail() == "" ){
            errors.reject("totaluser");
        }
    }

}
