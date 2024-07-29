package com.teamsix.firstteamproject.user.email;

public interface EmailService {
    String verifyEmail(String token);

    void sendUserEmail(String email);
}
