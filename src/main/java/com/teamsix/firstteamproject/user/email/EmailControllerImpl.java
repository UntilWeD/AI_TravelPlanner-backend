package com.teamsix.firstteamproject.user.email;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
@RequiredArgsConstructor
public class EmailControllerImpl implements EmailController {

    private  final EmailService emailService;
    private final  EmailTokenService emailTokenService;

    @Override
    @GetMapping("/confirm-email")
    public ResponseEntity viewConfirmEmail(String token) {
        try{
            //이메일 인증 로직을 실행합니다.
            log.info("[EmailControllerImpl]token = {}", token);

            boolean result = emailService.verifyEmail(token);
            log.info("[EmailControllerImpl]result = {}", result);


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/resend-email")
    public ResponseEntity resendEmail(@RequestBody String email){
        log.info("[EmailController] User's request to resend email.");

        emailTokenService.resendEmailToken(email);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @GetMapping ("/send-email")
    public ResponseEntity sendEmail(@RequestBody String email){
        log.info("[EmailController] User's request to send email.");
        try{
            email = extractEmail(URLDecoder.decode(email, "UTF-8"));
        } catch (Exception e){
            log.info("[EmailController] 디코딩 중 에러발생");
        }

        emailTokenService.resendEmailToken(email);

        return new ResponseEntity(HttpStatus.OK);
    }

    public static String extractEmail(String input) {
        // 입력 문자열이 null이거나 "email="을 포함하지 않으면 입력 문자열 그대로 반환
        if (input == null || !input.contains("email=")) {
            return input;
        }

        // "email=" 다음의 문자열을 추출하여 반환
        int startIndex = input.indexOf("email=") + "email=".length();
        return input.substring(startIndex);
    }


    // 아이디 및 비밀번호 찾는 메서드들

    @Override
    public ResponseEntity findPasswordByEmail(String email, BindingResult bindingResult) {
        log.info("[EmailController]findPasswordByEmail is Executing...");






        return new ResponseEntity(HttpStatus.OK);
    }
}
