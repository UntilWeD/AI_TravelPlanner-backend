package com.teamsix.firstteamproject.user.email;

import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailTokenServiceImpl implements EmailTokenService{
    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;
    private final UserRepository userRepository;

    @Override
    public boolean sendEmailToken(String email) {
        if(userRepository.findEmailVerificationByEmail(email)){
            return false;
        }
        Long userNumber = userRepository.findUserByEmail(email).get().getId();
        return createEmailToken(userNumber, email);
    }

    // http://ec2-43-203-192-225.ap-northeast-2.compute.amazonaws.com:8080/
    // http://localhost:8080/
    @Override
    public boolean createEmailToken(Long number, String receiverEmail) {
        log.info("[EmailTokenService] Making EmailToken...");

        //이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(number);
        emailTokenRepository.save(emailToken);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("" +
                "아래에 링크를 클릭해주셔서 이메일인증을 마무리 해주세요. \n\n\n" +
                "http://localhost:8080/user/email/confirm-email?token=" +
                emailToken.getId() +
                "\n\n\n\n\n 이용해주셔서 감사합니다.")
        ;
        emailSenderService.sendEmail(mailMessage);

        return true;
    }



    @Override
    public Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        if(emailToken.isEmpty()){
                emailTokenRepository.deleteEmailTokenById(emailTokenId);
        }

        return emailToken;
    }

}
