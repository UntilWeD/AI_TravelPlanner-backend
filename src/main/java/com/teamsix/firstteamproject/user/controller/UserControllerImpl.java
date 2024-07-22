package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.global.util.SecurityUtil;
import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.service.UserServiceImpl;
import com.teamsix.firstteamproject.user.validation.RegistryValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "User API")
public class UserControllerImpl implements UserController{

    private final UserServiceImpl userService;
    private final RegistryValidator registryValidator;

    @Operation(summary = "유저 로그인 후 테스트", description = "회원 가입 후 spring security를 거쳐 유저정보를 확인하는 API")
    @GetMapping("/test")
    public String test(){
        return SecurityUtil.getCurrentUsername();
    }


    @Operation(summary = "회원가입", description = "회원가입 API")
    @ResponseBody
    @Override
    @PostMapping("/register")
    public ResponseEntity<RegistryForm> register(@RequestBody RegistryForm registryForm, BindingResult bindingResult) {
        log.info("[UserController] Executing register method ");
        log.info("[UserController] {} ", registryForm.toString());

        registryValidator.validate(registryForm, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("The user registration information does not comply with the form! : {}", bindingResult);
            return ResponseEntity.badRequest().body(null);
        }

        RegistryForm registeredUser = userService.register(registryForm);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "로그인", description = "유저가 가입된 정보로 로그인하며 jwt토큰을 반환한다.")
    @ResponseBody
    @Override
    @PostMapping("/signIn")
    public ResponseEntity<JwtToken> signIn(@RequestBody LoginForm loginForm, BindingResult bindingResult) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);

        JwtToken jwtToken = userService.signIn(loginForm);

        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return ResponseEntity.ok().body(jwtToken);
    }




}
