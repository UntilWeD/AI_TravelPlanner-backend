package com.teamsix.firstteamproject.user.Controller;

import com.teamsix.firstteamproject.global.util.SecurityUtil;
import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.JwtToken;
import com.teamsix.firstteamproject.user.Service.UserServiceImpl;
import com.teamsix.firstteamproject.user.Validation.RegistryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController{

    private final UserServiceImpl userService;
    private final RegistryValidator registryValidator;

    @GetMapping("/test")
    public String test(){
        return SecurityUtil.getCurrentUsername();
    }


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
