package com.teamsix.firstteamproject.user.service.email;

import com.teamsix.firstteamproject.user.entity.EmailToken;
import com.teamsix.firstteamproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class EmailService{

    private final EmailTokenService emailTokenService;
    private final UserService userService;

    // 프론트엔드 배포 후 리턴방식 변경필요
    @Transactional
    public String verifyEmail(String token) {

        //이메일 토큰을 찾아온다
        Optional<EmailToken> findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        if (findEmailToken.isEmpty()){
            return "해당 이메일인증토큰은 만료되거나 존재하지 않는 토큰입니다.";
        }

        // 이메일토큰 확인 후 이메일인증 여부 수정
        findEmailToken.get().setTokenToUsed();
        userService.setEmailVerifyById(findEmailToken.get().getUser().getId());

        return "이메일 인증이 완료되었습니다.";

    }

}
