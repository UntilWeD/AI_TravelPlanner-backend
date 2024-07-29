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
@RequestMapping("/user/email")
@Slf4j
@Validated
@RequiredArgsConstructor
@Tag(name = "Email Authentication API", description = "Email Authentication")
public class EmailControllerImpl implements EmailController {

    private  final EmailService emailService;
    private final  EmailTokenService emailTokenService;

    @Override
    @GetMapping("/confirm-email")
    public ResponseEntity<String> viewConfirmEmail(@RequestParam String token) {
        log.info("[EmailControllerImpl] Executing viewConfirmEmail method...");

        return ResponseEntity.ok(emailService.verifyEmail(token));
    }

    @Operation(summary = "이메일 인증 요청", description = "해당 유저의 이메일을 파라미터로 인증이메일을 전송한다.")
    @Override
    @GetMapping ("/send-email")
    public ResponseEntity sendEmail(@RequestParam String email){
        log.info("[EmailController] Executing sendEmail method...");
        emailTokenService.sendEmailToken(email);

        return new ResponseEntity(HttpStatus.OK);
    }


    // 아이디 및 비밀번호 찾는 메서드들

    @Override
    public ResponseEntity findPasswordByEmail(String email, BindingResult bindingResult) {
        log.info("[EmailController]findPasswordByEmail is Executing...");

        return new ResponseEntity(HttpStatus.OK);
    }
}
