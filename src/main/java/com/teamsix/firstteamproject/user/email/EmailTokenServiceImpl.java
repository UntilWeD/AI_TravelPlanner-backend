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

    //이메일 인증 토큰 생성
    @Override
    public String createEmailToken(Long number, String receiverEmail) {
        log.info("[EmailTokenService] Making EmailToken...");

        Assert.notNull(number, "memberId는 필수적입니다.");
        Assert.notNull(receiverEmail, "recevierEmail은 필수적입니다.");

        //이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(number);
        emailTokenRepository.save(emailToken);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("" +
                "아래에 링크를 클릭해주셔서 이메일인증을 마무리 해주세요."+
                "http://localhost:8000/userlogin/confirm-email?token="+emailToken.getId());
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getId();
    }


    //유효한 토큰 가져오기
    @Override
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        return emailToken.orElseThrow(() -> new RuntimeException());
    }

    @Override
    public void resendEmailToken(String email) {
        Long userNumber = userRepository.findUserByEmail(email).get().getId();
        createEmailToken(userNumber, email);
        log.info("[EmailTokenService] Resending Compleete!  userNumber = {}, email = {} ", userNumber, email);
    }
}
