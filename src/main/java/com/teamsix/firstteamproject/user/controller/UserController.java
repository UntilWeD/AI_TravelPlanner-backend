package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserController {
    public ResponseEntity<RegistryForm> register(RegistryForm registryForm);
    public ResponseEntity<JwtToken> signIn(LoginForm loginForm);


}
