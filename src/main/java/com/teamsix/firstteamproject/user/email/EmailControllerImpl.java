package com.teamsix.firstteamproject.user.email;


import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResultDTO<String> viewConfirmEmail(@RequestParam String token) {
        log.info("[EmailControllerImpl] Executing viewConfirmEmail method...");

        return ApiUtils.ok(emailService.verifyEmail(token));
    }

    @Operation(summary = "이메일 인증 요청", description = "해당 유저의 이메일을 파라미터로 인증이메일을 전송한다.")
    @Override
    @GetMapping ("/send-email")
    public ResultDTO sendEmail(@RequestParam String email){
        log.info("[EmailController] Executing sendEmail method...");
        if(emailTokenService.sendEmailToken(email)){
            return ApiUtils.ok(null);
        } else {
            return ApiUtils.error(HttpStatus.BAD_REQUEST, "이미 이메일인증을 완료한 회원입니다.");
        }
    }


    // 아이디 및 비밀번호 찾는 메서드들

    @Override
    public ResultDTO findPasswordByEmail(String email, BindingResult bindingResult) {
        log.info("[EmailController]findPasswordByEmail is Executing...");

        return ApiUtils.ok(null);
    }
}
