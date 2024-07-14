package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.entity.User;

import java.util.Optional;

public interface UserService {
    public RegistryForm register(RegistryForm registryForm);
    public JwtToken signIn(LoginForm loginForm);
    public void logout(User user);
    public Optional<User> setEmailVerify(Long userId);


}
