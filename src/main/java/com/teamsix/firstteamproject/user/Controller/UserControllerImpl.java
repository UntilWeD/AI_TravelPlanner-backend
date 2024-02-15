package com.teamsix.firstteamproject.user.Controller;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Service.UserServiceImpl;
import com.teamsix.firstteamproject.user.Validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserControllerImpl implements UserController{

    private final UserServiceImpl userService;
    private final UserValidator userValidator;


    @ResponseBody
    @Override
    @PostMapping("/register")
    public ResponseEntity<User> register(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        log.info("[UserControllerImpl] Executing register method ");

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("The user registration information does not comply with the form! : {}", bindingResult);
            return ResponseEntity.badRequest().body(null);
        }

        User savedUser = userService.register(user);

        model.addAttribute("user", savedUser);

        return ResponseEntity.ok(savedUser);
    }

    @ResponseBody
    @Override
    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);




        return null;
    }
}
