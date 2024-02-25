package com.teamsix.firstteamproject.user.Controller;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Service.UserServiceImpl;
import com.teamsix.firstteamproject.user.Validation.RegistryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController{

    private final UserServiceImpl userService;
    private final RegistryValidator registryValidator;


    @ResponseBody
    @Override
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<RegistryForm> register(@RequestBody RegistryForm registryForm, BindingResult bindingResult, Model model) {
        log.info("[UserControllerImpl] Executing register method ");
        log.info("[UserControllerImpl] {} ", registryForm.toString());

        registryValidator.validate(registryForm, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("The user registration information does not comply with the form! : {}", bindingResult);
            return ResponseEntity.badRequest().body(null);
        }

        RegistryForm registeredUser = userService.register(registryForm);

        model.addAttribute("user", registeredUser);

        return ResponseEntity.ok(registeredUser);
    }

    @ResponseBody
    @Override
    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);


        return null;
    }
}
