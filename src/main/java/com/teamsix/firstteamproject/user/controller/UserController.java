package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.global.util.SecurityUtil;
import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.dto.UserDto;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "User API")
public class UserController{

    private final UserService userService;


    @Operation(summary = "유저 로그인 후 테스트", description = "회원 가입 후 spring security를 거쳐 유저정보를 확인하는 API")
    @GetMapping("/test")
    public String test(){
        return SecurityUtil.getCurrentUsername();
    }


    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/register")
    public ResultDTO<RegistryForm> register(@RequestBody RegistryForm registryForm) {
        return ApiUtils.ok(userService.register(registryForm));
    }

    @Operation(summary = "로그인", description = "유저가 가입된 정보로 로그인하며 jwt토큰을 반환한다.")
    @PostMapping("/signIn")
    public ResultDTO<JwtToken> signIn(@RequestBody LoginForm loginForm) {
        JwtToken jwtToken = userService.signIn(loginForm);
        return ApiUtils.ok(jwtToken);
    }

    @Operation(summary = "유저 홈", description = "유저가 자신의 개인정보를 수정하거나 여행플랜들을 확인한다.")
    @GetMapping("/{userId}")
    public ResultDTO<UserDto> userHome(@PathVariable Long userId){
        return ApiUtils.ok(userService.findUserById(userId));
    }



}
