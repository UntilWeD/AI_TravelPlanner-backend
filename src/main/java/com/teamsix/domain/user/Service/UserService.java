package com.teamsix.domain.user.Service;

import com.teamsix.domain.user.DTO.LoginForm;
import com.teamsix.domain.user.Entity.User;

public interface UserService {
    public User register(User user);
    public User login(LoginForm loginForm);
    public void logout(User user);

}
