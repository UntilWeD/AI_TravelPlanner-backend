package com.teamsix.firstteamproject.user.email;

import java.util.Optional;

public interface EmailTokenService {
    String createEmailToken(Long number, String receiverEmail);
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException;
    String sendEmailToken(String email);
}
