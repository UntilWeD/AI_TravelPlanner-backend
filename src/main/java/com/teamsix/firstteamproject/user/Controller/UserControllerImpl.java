package com.teamsix.firstteamproject.user.Controller;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Service.UserServiceImpl;
import com.teamsix.firstteamproject.user.Validation.RegistryValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController{

    private final UserServiceImpl userService;
    private final RegistryValidator registryValidator;


    @ResponseBody
    @Override
    @PostMapping("/register")
    public ResponseEntity<RegistryForm> register(@ModelAttribute RegistryForm registryForm, BindingResult bindingResult) {
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
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request,
    HttpServletResponse response) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);

        Optional<User> loginedUser = userService.login(loginForm);

        if(loginedUser == null){
            log.info("[UserControllerImpl] Login Error");
            return ResponseEntity.badRequest().body(null);
        } else{
            log.info("[UserControllerImpl] Login Sucessful");

            log.info("response = {}",response);

            return ResponseEntity.ok().body(loginedUser.get());
        }

    }




}
