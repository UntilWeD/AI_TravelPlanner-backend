package com.teamsix.firstteamproject.user.email;

import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
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
public class EmailServiceImpl implements EmailService {

    private final EmailTokenService emailTokenService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String verifyEmail(String token) {
        log.info("[EmailServiceImpl] verifyEmail is Executing");

        //이메일 토큰을 찾아온다
        Optional<EmailToken> findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        if (findEmailToken.isEmpty()){
            return "해당 이메일인증토큰은 만료되거나 존재하지 않는 토큰입니다.";
        }

        Optional<User> findUser = userService.setEmailVerify(findEmailToken.get().getUserId());
        findEmailToken.get().setTokenToUsed(); // 사용완료

        return "이메일 인증이 완료되었습니다.";

    }

    @Override
    public void sendUserEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user==null){
            log.info("[EmailService] User's Email is not Correct!");
        }


    }
}
