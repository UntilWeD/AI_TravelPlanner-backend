package com.teamsix.firstteamproject.user.email;

public interface EmailTokenService {
    String createEmailToken(Long number, String receiverEmail);
    EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException;
    void resendEmailToken(String email);
}
