package com.teamsix.firstteamproject.user.email;

import java.util.Optional;

public interface EmailTokenService {
    boolean createEmailToken(Long number, String receiverEmail);
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException;
    boolean sendEmailToken(String email);
}
