package com.teamsix.firstteamproject.user.email;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
@RequiredArgsConstructor
@Tag(name = "Email Authentication API", description = "Email Authentication")
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

    @Operation(summary = "이메일 인증 재요청", description = "이메일인증을 미뤘던 사용자에게 다시 메일을 보낼때")
    @Override
    @PostMapping("/resend-email")
    public ResponseEntity resendEmail(@RequestBody String email){
        log.info("[EmailController] User's request to resend email.");

        emailTokenService.resendEmailToken(email);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(summary = "이메일 인증 요청", description = "재인증하고 차이가 뭐지, 좀 봐야할듯")
    @Override
    @GetMapping ("/send-email")
    public ResponseEntity sendEmail(@RequestBody String email){
        log.info("[EmailController] User's request to send email.");
        try{
            email = extractEmail(URLDecoder.decode(email, StandardCharsets.UTF_8));
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
