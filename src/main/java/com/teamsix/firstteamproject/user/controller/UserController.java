package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserController {
    public ResultDTO<RegistryForm> register(RegistryForm registryForm);
    public ResultDTO<JwtToken> signIn(LoginForm loginForm);


}
