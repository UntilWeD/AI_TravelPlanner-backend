package com.teamsix.firstteamproject.user.service.email;

import com.teamsix.firstteamproject.user.entity.EmailToken;
import com.teamsix.firstteamproject.user.repository.EmailTokenRepository;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import com.teamsix.firstteamproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class EmailTokenService{
    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;
    private final UserService userService;
    private final String BACKEND_URL;

    public EmailTokenService(EmailSenderService emailSenderService, EmailTokenRepository emailTokenRepository,
                             UserService userService, @Value("${backend.url}") String BACKEND_URL) {
        this.emailSenderService = emailSenderService;
        this.emailTokenRepository = emailTokenRepository;
        this.userService = userService;
        this.BACKEND_URL = BACKEND_URL;
    }

    public boolean sendEmailToken(String email) {
        if(userService.findEmailVerificationByEmail(email)){
            return false;
        }
        return createEmailToken(userService.findUserByEmail(email).getId(), email);
    }

    public boolean createEmailToken(Long number, String receiverEmail) {
        //이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(number);
        emailTokenRepository.save(emailToken);


        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("" +
                "아래에 링크를 클릭해주셔서 이메일인증을 마무리 해주세요. \n\n\n" +
                BACKEND_URL+ "/user/email/verify?token=" +
                emailToken.getId() +
                "\n\n\n\n\n 이용해주셔서 감사합니다.");
        emailSenderService.sendEmail(mailMessage);

        return true;
    }


    public Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        // 이미 만료된 토큰 삭제
        if(emailToken.isEmpty()){
                emailTokenRepository.deleteEmailTokenById(emailTokenId);
        }

        return emailToken;
    }

}
