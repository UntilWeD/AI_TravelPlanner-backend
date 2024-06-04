package com.teamsix.firstteamproject.user.email;

import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Repository.UserRepository;
import com.teamsix.firstteamproject.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    private final EmailTokenService emailTokenService;
    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public boolean verifyEmail(String token) {
        log.info("[EmailSenderService] verifyEmail is Executing");

        //이메일 토큰을 찾아온다
        EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);

        //고유 유저 아이디 찾기
        Long userId = findEmailToken.getUserId();
        Optional<User> findUser = userService.setEmailVerify(userId);

        findEmailToken.setTokenToUsed(); // 사용완료

        if(findUser.isPresent()){
            log.info("유저가 존재합니다. 유저 : {}", findUser.get());
            return true;
        }
        log.info("해당유저는 데이터베이스에 존재하지 않습니다.");
        return false;

    }

    @Override
    public void sendUserEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user==null){
            log.info("[EmailService] User's Email is not Correct!");
        }


    }
}
