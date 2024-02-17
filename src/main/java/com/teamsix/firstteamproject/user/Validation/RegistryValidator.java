package com.teamsix.firstteamproject.user.Validation;

import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegistryForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistryForm user = (RegistryForm) target;

        //name
        if(user.getName().length() < 10 && user.getName().length() < 0){
            errors.rejectValue("name", "length");
        }

        //pw
        if(user.getPw().length() < 15 && user.getPw().length()  < 6){
            errors.rejectValue("pw", "length");
        }

        //TODO
        //email

        //전체 검증
        if(user.getName() == ""  || user.getPw() == "" || user.getEmail() == "" ){
            errors.reject("totaluser");
        }
    }

}
