package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;

import java.util.Optional;

public interface UserService {
    public RegistryForm register(RegistryForm registryForm);
    public Optional<User> login(LoginForm loginForm);
    public void logout(User user);
    public Optional<User> setEmailVerify(Long userId);



}
