package com.teamsix.firstteamproject.user.email;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        emailTokenService.resendEmailToken(email);

        return new ResponseEntity(HttpStatus.OK);
    }
}
