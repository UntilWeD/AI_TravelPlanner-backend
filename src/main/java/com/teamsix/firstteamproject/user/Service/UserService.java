package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;

public interface UserService {
    public RegistryForm register(RegistryForm registryForm);
    public User login(LoginForm loginForm);
    public void logout(User user);

}
