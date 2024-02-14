package com.teamsix.domain.user.Controller;

import com.teamsix.domain.user.DTO.LoginForm;
import org.springframework.validation.BindingResult;

public interface UserController {
    public String register();
    public String login(LoginForm loginForm, BindingResult bindingResult);

}
