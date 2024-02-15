package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.Entity.User;

public interface UserService {
    public User register(User user);
    public User login(LoginForm loginForm);
    public void logout(User user);

}
