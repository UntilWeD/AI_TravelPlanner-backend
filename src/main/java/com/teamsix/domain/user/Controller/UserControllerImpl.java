package com.teamsix.domain.user.Controller;

import com.teamsix.domain.user.DTO.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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


    @ResponseBody
    @Override
    public String register() {
        return null;
    }

    @ResponseBody
    @Override
    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);




        return null;
    }
}
